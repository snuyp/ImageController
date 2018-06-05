package com.example.dima.bsofttask.ui.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.common.Common;
import com.example.dima.bsofttask.common.ImageLab;
import com.example.dima.bsofttask.mvp.model.post.UserImage;
import com.example.dima.bsofttask.mvp.presenter.LocationPresenter;
import com.example.dima.bsofttask.mvp.presenter.SendPhotoPresenter;
import com.example.dima.bsofttask.mvp.view.SendView;
import com.example.dima.bsofttask.mvp.view.SetUpLocationView;


public class PhotoViewFragment extends MvpDialogFragment implements SetUpLocationView,SendView{
    @InjectPresenter
    LocationPresenter locationPresenter;
    @InjectPresenter
    SendPhotoPresenter sendPhotoPresenter;

    TextView titlePhoto;
    private int latitude, longitude;

    public static PhotoViewFragment newInstance(ImageLab imageLab) {
        Bundle args = new Bundle();
        args.putSerializable(Common.ARG_PHOTO_ID, imageLab);
        PhotoViewFragment fragment = new PhotoViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.photo_view_fragment, null);
        final ImageLab imageLab = (ImageLab) getArguments().getSerializable(Common.ARG_PHOTO_ID);

        ImageView photoView = v.findViewById(R.id.photo_view);
        photoView.setImageBitmap(BitmapFactory.decodeFile(imageLab.getFile().getAbsolutePath()));
        locationPresenter.setFusedLocationProvider(getActivity());

        titlePhoto = v.findViewById(R.id.title_photo_name);
        //titlePhoto.s
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    UserImage userImage = new UserImage(null, latitude, longitude);
                                    sendPhotoPresenter.toSendPhoto(userImage,imageLab);
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //back and delete file...
                            }
                        }
                )
                .create();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    Common.PERMISSIONS_REQUEST_CODE);
        } else {
            locationPresenter.buildLocationCallback();
            locationPresenter.createLocationRequest();
            locationPresenter.onDisplayLastLocation();
        }

    }

    @Override
    public void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, Common.PERMISSIONS_REQUEST_CODE);
        } else {
            locationPresenter.buildLocationCallback();
            locationPresenter.createLocationRequest();
            locationPresenter.onDisplayLastLocation();
        }
    }

    @Override
    public void displayLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        else
        {
            locationPresenter.onDisplayLastLocation();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationPresenter.closeLocation();
    }

    @Override
    public void getLocation(Location location) {

        String cords = String.valueOf(location.getLongitude()+" "+location.getLatitude());
        latitude = (int) location.getLatitude();
        longitude = (int) location.getLongitude();
        titlePhoto.setText(cords);
    }
}
