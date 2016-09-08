package br.com.meuscontatos.principal.fragment;

import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.adapter.TabsAdapter;
import br.com.meuscontatos.principal.util.SlidTabLayout;

public class TabContatosPrincipalFragment extends android.support.v4.app.Fragment {

    SlidTabLayout mSlidingTabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_contatos_principal_fragment,container,false);

        viewPager = (ViewPager) view.findViewById(R.id.vp_tabs);
        viewPager.setAdapter(new TabsAdapter(getFragmentManager(),getContext()));
        mSlidingTabLayout = (SlidTabLayout) view.findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.white));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mSlidingTabLayout.refreshDrawableState();
        mSlidingTabLayout.setViewPager(viewPager);
        return view;
    }

}
