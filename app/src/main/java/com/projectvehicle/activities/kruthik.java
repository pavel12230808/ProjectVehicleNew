package com.projectvehicle.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.projectvehicle.R;

import java.io.IOException;

public class kruthik extends AppCompatActivity {
    int PICK_IMAGE_REQUEST = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kruthik);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImg = data.getData();
            Bitmap b;
            try {
                 b = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImg);
                if (b != null) {
                    InputImage image = InputImage.fromBitmap(b, 0);
                    TextRecognizer recognizer = TextRecognition.getClient();
                    Task<Text> result =
                            recognizer.process(image)
                                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                                        @Override
                                        public void onSuccess(Text visionText) {
                                            for (Text.TextBlock block : visionText.getTextBlocks()) {
                                                Rect boundingBox = block.getBoundingBox();
                                                Point[] cornerPoints = block.getCornerPoints();
                                                String text = block.getText();
                                                Toast.makeText(kruthik.this,text,Toast.LENGTH_LONG).show();
                                                for (Text.Line line : block.getLines()) {
                                                    // ...
                                                    for (Text.Element element : line.getElements()) {
                                                        // ...
                                                    }
                                                }
                                            }
                                        }
                                    })
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Task failed with an exception
                                                    // ...
                                                }
                                            });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}