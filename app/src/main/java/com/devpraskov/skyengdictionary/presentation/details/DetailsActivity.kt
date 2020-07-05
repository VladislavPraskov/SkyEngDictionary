package com.devpraskov.skyengdictionary.presentation.details

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.devpraskov.skyengdictionary.R
import com.devpraskov.skyengdictionary.models.SearchUiModel
import com.devpraskov.skyengdictionary.utils.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsActivity : AppCompatActivity() {

    companion object {
        const val MODEL = "details_activity_model"
        fun start(context: Context, model: SearchUiModel) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(MODEL, model)
            context.startActivity(intent)
        }
    }

    lateinit var snackbar: Snackbar
    private var mediaPlayer: MediaPlayer? = MediaPlayer()
    private val viewModel: DetailsViewModel by viewModel()
    private val adapter = DetailsListAdapter()
    var currentMeaningId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        statusBarColor(getColorCompat(R.color.first_color))
        initView()
        initObservers()
    }

    private fun initView() {
        val model = intent.getParcelableExtra<SearchUiModel>(MODEL)!!
        currentMeaningId = model.meaningId
        viewModel.setHeaderLiveDat(model)
        viewModel.loadMeanings(currentMeaningId)
        recyclerView?.apply {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = this@DetailsActivity.adapter
        }
        snackbar = Snackbar.make(mainContainer, "", Snackbar.LENGTH_LONG)
    }

    private fun initObservers() {
        viewModel.getHeaderLiveData().observe(this, Observer { model ->
            setImage(model.imageUrl)
            word?.text = model.word
            transcription?.text = model.transcription
            image?.onClick { setImage(model.imageUrl, true) }
            sound.onClick {
                try {
                    mediaPlayer?.setDataSource(this, Uri.parse(model.audioUrl))
                    mediaPlayer?.prepareAsync()
                    mediaPlayer?.setOnPreparedListener { it?.start() }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })
        viewModel.getMeaningsLiveData().observe(this, Observer { action ->
            when (action) {
                is DetailsAction.Success -> {
                    group?.show()
                    adapter.submitList(action.model.meanings)
                    definition?.text = action.model.definition
                    definition?.visibilityGone(action.model.definition.isNotEmpty())
                    definitionTitle?.visibilityGone(action.model.definition.isNotEmpty())
                    definition?.text = action.model.definition
                    examples?.text = action.model.examples
                    meaningProgress?.hide()
                    meaning?.visibilityGone(action.model.meanings.isNotEmpty())
                    frequency?.visibilityGone(action.model.meanings.isNotEmpty())
                }
                is DetailsAction.Error -> {
                    snackbar.setText(action.error)
                    if (!snackbar.isShown) snackbar.show()
                    group?.hide()
                    meaningProgress?.hide()
                }
                is DetailsAction.Loading -> {
                    meaningProgress?.show()
                }
            }
        })
    }


    private fun setImage(imageUrl: String?, isClicked: Boolean = false) {
        progressBar?.show()
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(16))
        Glide.with(image)
            .load(imageUrl)
            .error(R.drawable.broken_image)
            .fallback(R.drawable.photo)
            .apply {
                if (isClicked) placeholder(image?.drawable)
                else placeholder(R.drawable.photo)
            }
            .apply(requestOptions)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    image?.isClickable = true
                    progressBar?.hide()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    image?.isClickable = false
                    progressBar?.hide()
                    return false
                }

            })
            .into(image)
    }

    private fun releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer?.release()
                mediaPlayer = null
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        releaseMP()
    }
}
