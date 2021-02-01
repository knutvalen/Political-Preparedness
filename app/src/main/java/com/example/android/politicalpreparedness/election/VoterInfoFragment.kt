package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.text.DateFormat
import java.util.*

class VoterInfoFragment : Fragment() {

    private lateinit var binding: FragmentVoterInfoBinding
    private val viewModel: VoterInfoViewModel by viewModel()
    private var electionID: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.viewModel = viewModel

        val electionID = VoterInfoFragmentArgs.fromBundle(requireArguments()).argElectionId
        val address = VoterInfoFragmentArgs.fromBundle(requireArguments()).argDivision.toFormattedString()
        viewModel.refreshVoterinfo(electionID, address)
        this.electionID = electionID

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        viewModel.voterinfo.observe(viewLifecycleOwner, { voterinfo ->
            Timber.d("voterinfo observer: $voterinfo")

            val electionName = voterinfo.election.name
            val electionDate = DateFormat.getDateInstance(DateFormat.MEDIUM)
                .format(voterinfo.election.electionDay)
                .capitalize(Locale.getDefault())
            val votingLocationURL = voterinfo.state?.get(0)?.electionAdministrationBody?.votingLocationFinderUrl
            val ballotURL = voterinfo.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl
            val correspondenceAddress = voterinfo.state?.get(0)?.electionAdministrationBody?.correspondenceAddress

            binding.electionName.title = electionName
            binding.electionDate.text = electionDate

            if (votingLocationURL != null || ballotURL != null) {
                binding.stateHeader.text = getString(R.string.title_election_information)
            } else {
                binding.stateHeader.visibility = View.GONE
            }

            if (votingLocationURL != null) {
                binding.stateLocations.text = votingLocationURL
            } else {
                binding.stateLocations.visibility = View.GONE
            }

            if (ballotURL != null) {
                binding.stateBallot.text = ballotURL
            } else {
                binding.stateBallot.visibility = View.GONE
            }

            if (correspondenceAddress != null) {
                binding.stateCorrespondenceHeader.text = getString(R.string.title_state_correspondence)
                binding.address.text = correspondenceAddress.toFormattedString()
            } else {
                binding.address.visibility = View.GONE
            }

        })

        viewModel.savedElections.observe(viewLifecycleOwner, { savedElections ->
            Timber.d("savedElections observer: $savedElections")

            val isSaved = savedElections.map { it.id }.contains(electionID)

            if (isSaved) {
                binding.button.text = getString(R.string.unfollow_election)
            } else {
                binding.button.text = getString(R.string.follow_election)
            }
        })
    }

}