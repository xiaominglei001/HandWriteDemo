package handwritedemo.example.com.handwritedemo;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 参考:http://hbxflihua.iteye.com/blog/1512765
 * */

public class HandwritingActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Bitmap mSignBitmap;
    private String signPath;
    private ImageView ivSign;
    private TextView tvSign;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("欢迎使用手写签名!");
        ivSign = (ImageView) findViewById(R.id.iv_sign);
        tvSign = (TextView) findViewById(R.id.tv_sign);

        ivSign.setOnClickListener(signListener);
        tvSign.setOnClickListener(signListener);
    }


    private OnClickListener signListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            WritePadDialog writeTabletDialog = new WritePadDialog(
                    HandwritingActivity.this, new DialogListener() {
                @Override
                public void refreshActivity(Object object) {

                    mSignBitmap = (Bitmap) object;
                    signPath = createFile();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 2;  // 图片的大小设置为原来的2分之一再显示
                    Bitmap zoombm = BitmapFactory.decodeFile(signPath, options);
                    ivSign.setImageBitmap(zoombm);
                    tvSign.setVisibility(View.GONE);
                }
            });
            writeTabletDialog.show();
        }
    };

    /**
     * 创建手写签名文件
     *
     * @return
     */
    private String createFile() {
        ByteArrayOutputStream baos = null;
        String _path = null;
        try {
            String sign_dir = Environment.getExternalStorageDirectory() + File.separator;
            _path = sign_dir + System.currentTimeMillis() + ".jpg";
            baos = new ByteArrayOutputStream();
            mSignBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] photoBytes = baos.toByteArray();
            if (photoBytes != null) {
                new FileOutputStream(new File(_path)).write(photoBytes);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return _path;
    }
}
