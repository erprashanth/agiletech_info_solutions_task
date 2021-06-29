package com.interview.task.module.main

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.interview.task.R
import com.interview.task.databinding.ItemImagesBinding
import com.interview.task.dto.Photo
import com.interview.task.utils.ImageUtils

class ImagesAdapter(private val itemListener: OnPhotoClickListener) : ListAdapter<Photo,
        ImagesAdapter.ReminderListViewHolder>(ReminderListDiffUtil()) {

    private var parentList : ArrayList<Photo> = ArrayList()

    interface OnPhotoClickListener{
        fun photoClicked(photo: Photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderListViewHolder {
        return ReminderListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_images, parent, false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ReminderListViewHolder, position: Int) {
        getItem(position).let { reminderDetail ->
            holder.bind(reminderDetail,itemListener)
        }
    }

    fun updatePhotoList(photoList: List<Photo>?) {
        parentList = photoList as ArrayList<Photo>
        submitList(photoList)
        notifyDataSetChanged()
    }

    fun addPhotoList(photoList: List<Photo>?){
        parentList.addAll(photoList as ArrayList)
        submitList(parentList)
        notifyDataSetChanged()
    }

    class ReminderListViewHolder(private val binding: ItemImagesBinding) : RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(photo: Photo, listener : OnPhotoClickListener) {
            with(binding) {

                Glide.with(binding.root.context)
                    .load(ImageUtils.getImageUrlById(photo.farm,photo.id,photo.secret))
                    .error(R.drawable.ic_baseline_image_24)
                    .into(binding.imageVw)

                binding.photo = photo
                binding.listener = listener
                executePendingBindings()
            }
        }
    }
}

private class ReminderListDiffUtil : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(
        oldItem: Photo,
        newItem: Photo
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Photo,
        newItem: Photo
    ): Boolean {
        return (oldItem.id == newItem.id) && (oldItem.title == newItem.title)
    }
}



