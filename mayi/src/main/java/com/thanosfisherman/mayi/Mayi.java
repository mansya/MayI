package com.thanosfisherman.mayi;

import android.support.v4.app.FragmentActivity;

import com.thanosfisherman.mayi.listeners.IPermissionListener;
import com.thanosfisherman.mayi.listeners.MayiErrorListener;
import com.thanosfisherman.mayi.listeners.PermissionResultListener;
import com.thanosfisherman.mayi.listeners.RationaleListener;

public class Mayi implements IPermissionListener, IPermissionListener.Permission, IPermissionListener.SinglePermissionListener
{
    private static final String TAG = Mayi.class.getSimpleName();
    private PermissionBean[] mPermissions = null;
    private String mPermission;
    private PermissionResultListener mPermissionsResultListener;
    private RationaleListener mRationaleListener;
    private MayiFragment mFrag;

    private Mayi(FragmentActivity activity)
    {
        initialize(activity);
    }

    private void initialize(FragmentActivity activity)
    {
        mFrag = (MayiFragment) activity.getSupportFragmentManager().findFragmentByTag(MayiFragment.TAG);
        if (mFrag == null)
        {
            mFrag = new MayiFragment();
            mFrag.setRetainInstance(true);
            activity.getSupportFragmentManager().beginTransaction().add(mFrag, MayiFragment.TAG).commitNow();
        }
    }

    public static IPermissionListener.Permission withActivity(FragmentActivity activity)
    {
        return new Mayi(activity);
    }


    @Override
    public SinglePermissionListener withPermission(String permission)
    {
        mPermission = permission;
        return this;
    }

    @Override
    public SinglePermissionListener onPermissionResult(PermissionResultListener response)
    {
        mPermissionsResultListener = response;
        return this;
    }

    @Override
    public SinglePermissionListener onPermissionRationaleShouldBeShown(RationaleListener rationale)
    {
        mRationaleListener = rationale;
        return this;
    }

    @Override
    public IPermissionListener onErrorListener(MayiErrorListener errorListener)
    {
        return this;
    }

    @Override
    public void check()
    {
        mFrag.checkPermissions(mPermission);
    }
}



 /*   *//**
 * Set the permissions to be checked during  {@link #requestAll(IPermissionsAllListener)}
 *
 * @param permissionsStr - the permissions string array
 * <p>
 * Check whether ALL permissions are granded or not.
 * <p>
 * Check that all given permissions have been granted by verifying that each entry in the
 * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
 * <p>
 * Check whether ALL permissions are granded or not.
 * <p>
 * Check that all given permissions have been granted by verifying that each entry in the
 * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
 * <p>
 * Check whether ALL permissions are granded or not.
 * <p>
 * Check that all given permissions have been granted by verifying that each entry in the
 * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
 *//*
    public Mayi setPermissions(String... permissionsStr)
    {
        mPermissions = new PermissionBean[permissionsStr.length];
        for (int i = 0; i < permissionsStr.length; i++)
            mPermissions[i] = new PermissionBean(permissionsStr[i]);
        return this;
    }

    *//**
 * Check whether ALL permissions are granded or not.
 *//*
    public void requestAll(IPermissionsAllListener listener)
    {
        if (mPermissions == null)
            throw new RuntimeException("String[] permissions is null, please call setPermissions first!");
        this.mListenerAll = listener;
        //if it has the permissions, don't wait for async callback and
        //just return true so apps can continue as normal
        if (verifyAllPermissionsGranted())
            listener.permissionResults(true);
        else
            requestAllPermissionsHelper();
    }

    public void requestEach(IPermissionsEachListener listener)
    {
        if (mPermissions == null)
            throw new RuntimeException("String[] permissions is null, please call setPermissions first!");
        this.mListenerEach = listener;
        if (verifyAllPermissionsGranted(mPermissions))
            listener.permissionResults(mPermissions);
        else
            requestAllPermissionsHelper();
    }

    private void requestAllPermissionsHelper()
    {
        final String[] permissions = new String[mPermissions.length];
        for (int i = 0; i < mPermissions.length; i++)
            permissions[i] = mPermissions[i].getName();
        //requestPermissions(permissions, PERMISSION_REQUEST_CODE);
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Log.d(TAG, "onRequestPermissionsResult() " + Arrays.toString(permissions) + Arrays.toString(grantResults));

        if (grantResults.length == 0)
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        else if (requestCode == PERMISSION_REQUEST_CODE && verifyAllPermissionsFromResult(grantResults, mPermissions))
        {
            if (mListenerAll != null)
                mListenerAll.permissionResults(true);
            if (mListenerEach != null)
                mListenerEach.permissionResults(mPermissions);
        }
        else
        {
            if (mListenerAll != null)
                mListenerAll.permissionResults(false);
            if (mListenerEach != null)
                mListenerEach.permissionResults(mPermissions);
        }
    }

    */

/**
 * Check that all given permissions have been granted by verifying that each entry in the
 * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
 *//*
    private boolean verifyAllPermissionsFromResult(int[] grantResults, PermissionBean... bean)
    {
        boolean allGranted = true;
        if (grantResults.length != bean.length)
            return false;
        for (int i = 0; i < grantResults.length; i++)
        {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
            {
                bean[i].setGranted(true);
                bean[i].setShouldShowRequestPermissionRationale(false);
            }
            else
            {
                allGranted = false;
                bean[i].setGranted(false);
                bean[i].setShouldShowRequestPermissionRationale(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), bean[i].getName()));
            }
        }
        return allGranted;
    }

    private boolean verifyAllPermissionsGranted(PermissionBean... permissionBean)
    {
        boolean allGranted = true;

        for (PermissionBean bean : permissionBean)
        {
            if (ActivityCompat.checkSelfPermission(getContext(), bean.getName()) == PackageManager.PERMISSION_GRANTED)
            {
                bean.setGranted(true);
                bean.setShouldShowRequestPermissionRationale(false);
            }
            else
            {
                allGranted = false;
                bean.setGranted(false);
                bean.setShouldShowRequestPermissionRationale(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), bean.getName()));
            }
        }
        return allGranted;
    }

    public boolean verifyAllPermissionsGranted()
    {
        if (mPermissions == null)
            throw new RuntimeException("String[] permissions is null, please call setPermissions first!");
        final String[] permissionsStr = new String[mPermissions.length];
        for (int i = 0; i < mPermissions.length; i++)
            permissionsStr[i] = mPermissions[i].getName();
        for (String permission : permissionsStr)
            if (ActivityCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    public boolean verifyAllPermissionsGranted(String... permissionsStr)
    {
        if (permissionsStr == null)
            throw new RuntimeException("String[] permissions is null, please call setPermissions first!");
        // Verify that all required permissions have been granted
        for (String permission : permissionsStr)
            if (ActivityCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }*/
