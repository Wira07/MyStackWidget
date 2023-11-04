package com.yudawahfiudin.storyapp.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yudawahfiudin.storyapp.R
import com.yudawahfiudin.storyapp.databinding.ItemListStoryBinding
import com.yudawahfiudin.storyapp.model.StoryModel
import com.yudawahfiudin.storyapp.remote.ListStoryItem
import com.yudawahfiudin.storyapp.utils.getTimeLineUploaded
import com.yudawahfiudin.storyapp.view.detail.DetailActivity

class StoriesAdapter: PagingDataAdapter<ListStoryItem,StoriesAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    private var listener: ((StoryModel, ItemListStoryBinding) -> Unit)? = null

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = ItemListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }
    }

    class StoryViewHolder(private val binding: ItemListStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(story: ListStoryItem) {
            with(binding) {
                tvName.text = story.name
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(binding.imgPoster)
                tvTimeUpload.text = " ${itemView.context.getString(R.string.text_uploaded)} ${
                    getTimeLineUploaded(
                        itemView.context,
                        story.createdAt.toString()
                    )
                }"
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.DATA_STORY, story)
                }
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.tvName, "name"),
                        Pair(binding.imgPoster, "image"),
                        Pair(binding.tvTimeUpload, "time")
                    )
                it.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    fun onClick(listener: ((StoryModel, ItemListStoryBinding) -> Unit)?) {
        this.listener = listener
    }
}