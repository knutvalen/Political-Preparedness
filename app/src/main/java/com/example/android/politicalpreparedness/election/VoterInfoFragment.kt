package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.*
import android.widget.Toast
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
            val correspondenceAddress = voterinfo.state?.get(0)?.electionAdministrationBody?.correspondenceAddress?.toFormattedString()

            viewModel.electionName.value = electionName
            viewModel.electionDate.value = electionDate
            viewModel.votingLocationURL.value = votingLocationURL
            viewModel.ballotURL.value = ballotURL
            viewModel.correspondenceAddress.value = correspondenceAddress

            if (votingLocationURL != null || ballotURL != null) {
                viewModel.electionInformationVisibility.value = View.VISIBLE
            } else {
                viewModel.electionInformationVisibility.value = View.GONE
            }

            if (correspondenceAddress != null) {
                viewModel.correspondenceVisibility.value = View.VISIBLE
            } else {
                viewModel.correspondenceVisibility.value = View.GONE
            }

            viewModel.buttonVisibility.value = View.VISIBLE
        })

        viewModel.savedElections.observe(viewLifecycleOwner, { savedElections ->
            Timber.d("savedElections observer: $savedElections")

            val isSaved = savedElections.map { it.id }.contains(electionID)

            if (isSaved) {
                viewModel.buttonText.value = getString(R.string.unfollow_election)
            } else {
                viewModel.buttonText.value = getString(R.string.follow_election)
            }
        })

        viewModel.apiError.observe(viewLifecycleOwner, { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                viewModel.displayErrorMessageComplete()
            }
        })
    }

}