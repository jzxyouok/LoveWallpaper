package com.liuguilin.lovewallpaper.fragment;
/*
 *  项目名：  LoveWallpaper 
 *  包名：    com.liuguilin.lovewallpaper.fragment
 *  文件名:   AlbumFragment
 *  创建者:   LGL
 *  创建时间:  2017/1/13 15:49
 *  描述：    相册
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kymjs.rxvolley.toolbox.FileUtils;
import com.liuguilin.lovewallpaper.R;
import com.liuguilin.lovewallpaper.activity.GalleryActivity;
import com.liuguilin.lovewallpaper.adapter.AlbumGridAdapter;

import java.util.ArrayList;

public class AlbumFragment extends Fragment {

    //本地相册路径
    private String file = FileUtils.getSDCardPath() + "/LoveWallpaper/";
    private GridView mGridView;
    //相册路径
    private ArrayList<String> paths = new ArrayList<>();
    private AlbumGridAdapter albumGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mGridView = (GridView) view.findViewById(R.id.mGridView);
        getAllImagePath();
        albumGridAdapter = new AlbumGridAdapter(getActivity(),paths);
        mGridView.setAdapter(albumGridAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                intent.putExtra("position", i);
                intent.putStringArrayListExtra("bigUrl", paths);
                startActivity(intent);
            }
        });
    }

    //获取本地相册
    private void getAllImagePath() {
        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        //遍历相册
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            //将图片路径添加到集合
            paths.add(path);
        }
        cursor.close();
    }
}
