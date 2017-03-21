package edu.zcs.com.educationsys.util.tools;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */

public class UploadImage {
//    private String newName;
//            = "image.jpg";
    private List<String> uploadFiles;
//        "/sdcard/image.JPG";
    private String actionUrl;
//        "http://192.168.0.71:8086/HelloWord/myForm"

    public UploadImage(List<String> uploadFiles, String actionUrl) {
        this.uploadFiles = uploadFiles;
        this.actionUrl = actionUrl;
    }

    /* @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_upload);

        mText1 = (TextView) findViewById(R.id.myText2);
        //"文件路径：\n"+
        mText1.setText(uploadFile);
        mText2 = (TextView) findViewById(R.id.myText3);
        //"上传网址：\n"+
        mText2.setText(actionUrl);

        mButton = (Button) findViewById(R.id.myButton);
        mButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                uploadFile();
            }
        });
    }*/


    public  String uploadFile(){
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try
        {
            URL url =new URL(actionUrl);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();

            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);

            con.setRequestMethod("POST");

            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary="+boundary);

            DataOutputStream ds =
                    new DataOutputStream(con.getOutputStream());
            for (int i = 0; i < uploadFiles.size(); i++) {
                String filename = uploadFiles.get(i).substring(uploadFiles.lastIndexOf("//") + 1);
                ds.writeBytes(twoHyphens + boundary + end);
                ds.writeBytes("Content-Disposition: form-data; " +
                        "name=\"file"+i+"\";filename=\"" +
                        filename + "\"" + end);
                ds.writeBytes(end);

                FileInputStream fStream = new FileInputStream(filename);

                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];

                int length = -1;

                while ((length = fStream.read(buffer)) != -1) {

                    ds.write(buffer, 0, length);
                }
                ds.writeBytes(end);
                ds.writeBytes(twoHyphens + boundary + twoHyphens + end);


                fStream.close();
            }
            ds.flush();


            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b =new StringBuffer();
            while( ( ch = is.read() ) != -1 )
            {
                b.append( (char)ch );
            }

            ds.close();

            return ("上传成功"+b.toString().trim());

        }
        catch(Exception e)
        {
            return ("上传失败"+e);
        }
    }
}
