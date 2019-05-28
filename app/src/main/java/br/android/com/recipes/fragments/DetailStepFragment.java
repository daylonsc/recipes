package br.android.com.recipes.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import br.android.com.recipes.DetailRecipeActivity;
import br.android.com.recipes.DetailStepActivity;
import br.android.com.recipes.MainActivity;
import br.android.com.recipes.R;
import br.android.com.recipes.bean.Recipe;
import br.android.com.recipes.bean.Step;


public class DetailStepFragment extends Fragment {
    private Step mStep;
    private Recipe mRecipe;
    private SimpleExoPlayerView exoPlayerView;
    private SimpleExoPlayer exoPlayer;
    private TextView textViewDescription;
    private Button buttonNext;
    private Button buttonBack;
    private static MediaSessionCompat mMediaSession;
    private static final String TAG = DetailStepActivity.class.getSimpleName();
    private PlaybackStateCompat.Builder mStateBuilder;

    public DetailStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_step, container, false);
        if(mStep == null){
            mStep = (Step) getActivity().getIntent().getParcelableExtra(DetailRecipeActivity.EXTRA_STEP);
            mRecipe = (Recipe) getActivity().getIntent().getParcelableExtra(MainActivity.EXTRA_RECIPE);
        }
        initializeMediaSession();
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        exoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.exo_player_view);
        textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
        buttonBack = (Button) view.findViewById(R.id.back_button);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Step step = getBackStep();
                if (step == null) {
                    Toast.makeText(getContext(), R.string.msg_first_step, Toast.LENGTH_LONG).show();
                } else {
                    releasePlayer();
                    setValues();
                }

            }
        });
        buttonNext = (Button) view.findViewById(R.id.next_button);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Step step = getNextStep();
                if (step == null) {
                    Toast.makeText(getContext(), R.string.msg_last_step, Toast.LENGTH_LONG).show();
                } else {
                    releasePlayer();
                    setValues();
                }

            }
        });
        setValues();
        ;
    }

    private void setValues() {
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            DefaultTrackSelector trackSelector;
            trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            Uri videoURI = Uri.parse(mStep.getVideoURL());

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);

            textViewDescription.setText(mStep.getDescription());
        } catch (Exception e) {
            Log.e("DetailStepFragment", " exoplayer error " + e.toString());
        }
    }

    private Step getBackStep() {
        int index = getIndexFromList();

        if (index == 0 || index == -1) {
            return null;
        } else {
            setStep(mRecipe.getSteps().get(index - 1));
        }
        return mStep;
    }

    private Step getNextStep() {
        int index = getIndexFromList();
        int size = mRecipe.getSteps().size();

        if (index == -1 || (index + 1) >= size) {
            return null;
        } else {
            setStep(mRecipe.getSteps().get(index + 1));
        }
        return mStep;
    }

    private int getIndexFromList() {
        for (int i = 0; i < mRecipe.getSteps().size(); i++) {
            if (mRecipe.getSteps().get(i).getId() == mStep.getId()) {
                return i;
            }
        }
        return -1;
    }

    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
    }

    public void setStep(Step step) {
        mStep = step;
    }

    private void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getActivity(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

    }


}
