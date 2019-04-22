package com.project.hyperfood.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.project.hyperfood.R;
import com.project.hyperfood.databinding.ActivityTutorialBinding;
import com.rd.animation.type.AnimationType;

public class TutorialActivity extends AbstractActivity{

    private ActivityTutorialBinding binding;
    private int[] tutorial = {};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutorial);

        TutorialPagerAdapter adapter = new TutorialPagerAdapter();
        binding.viewPager.setAdapter(adapter);

        binding.pageIndicatorView.setViewPager(binding.viewPager);
        binding.pageIndicatorView.setRadius(5);
        binding.pageIndicatorView.setAnimationDuration(300);
        binding.pageIndicatorView.setAnimationType(AnimationType.WORM);
        binding.pageIndicatorView.setInteractiveAnimation(true);
        binding.pageIndicatorView.setSelectedColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        binding.pageIndicatorView.setUnselectedColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    private class TutorialPagerAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        public TutorialPagerAdapter() {
            this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return tutorial.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = inflater.inflate(R.layout.view_item_tutorial, container, false);
            ImageView ivTutorial = itemView.findViewById(R.id.iv_tutorial);
            Glide.with(getContext()).asBitmap().load(tutorial[position]).into(ivTutorial);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }
}
