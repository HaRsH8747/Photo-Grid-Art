package neo.photogridart.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;
import com.flask.colorpicker.ColorPickerView.WHEEL_TYPE;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import neo.photogridart.R;
import neo.photogridart.canvastext.FontCache;
import neo.photogridart.canvastext.GridViewAdapter;
import neo.photogridart.canvastext.TextData;

public class FontFragment extends Fragment implements OnClickListener {
    private static final String TAG = "FontFragment";
    Activity activity;
    GridViewAdapter customGridAdapter;
    EditText editText;
    FontChoosedListener fontChoosedListener;
    public String[] fontPathList;
    TextData textData;
    TextView textView;

    public interface FontChoosedListener {
        void onOk(TextData textData);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_font, container, false);
        this.activity = getActivity();
        Bundle extras = getArguments();
        if (extras != null) {
            this.textData = (TextData) extras.getSerializable("text_data");
        }
        this.fontPathList = new String[]{"fonts/MfStillKindaRidiculous.ttf", "fonts/ahundredmiles.ttf", "fonts/Binz.ttf",
                "fonts/Blunt.ttf", "fonts/FreeUniversal-Bold.ttf", "fonts/gtw.ttf", "fonts/HandTest.ttf", "fonts/Jester.ttf",
                "fonts/Semplicita_Light.otf", "fonts/OldFolksShuffle.ttf", "fonts/vinque.ttf", "fonts/Primal _ream.otf",
                "fonts/Junction 02.otf", "fonts/Laine.ttf", "fonts/NotCourierSans.otf", "fonts/OSP-DIN.ttf", "fonts/otfpoc.otf",
                "fonts/Sofia-Regular.ttf", "fonts/Quicksand-Regular.otf", "fonts/Roboto-Thin.ttf", "fonts/RomanAntique.ttf",
                "fonts/SerreriaSobria.otf", "fonts/Strato-linked.ttf", "fonts/waltographUI.ttf", "fonts/CaviarDreams.ttf",
                "fonts/GoodDog.otf", "fonts/Pacifico.ttf", "fonts/Windsong.ttf", "fonts/digiclock.ttf"};
        this.textView = (TextView) fragmentView.findViewById(R.id.textview_font);
        this.textView.setPaintFlags(this.textView.getPaintFlags() | 128);
        this.textView.setOnClickListener(this);
        this.editText = (EditText) fragmentView.findViewById(R.id.edittext_font);
        editText.requestFocus();
        this.editText.setInputType(this.editText.getInputType() | 524288 | 176);

        this.editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence message, int start, int before, int count) {
                if (message.toString().compareToIgnoreCase("") != 0) {
                    FontFragment.this.textView.setText(message.toString());
                } else {
                    FontFragment.this.textView.setText(TextData.defaultMessage);
                }
                FontFragment.this.editText.setSelection(FontFragment.this.editText.getText().length());
            }

            public void afterTextChanged(Editable s) {
                FontFragment.this.editText.setSelection(FontFragment.this.editText.getText().length());
            }
        });
        this.editText.setFocusableInTouchMode(true);
        if (this.textData == null) {
            this.textData = new TextData(this.activity.getResources().getDimension(R.dimen.myFontSize));
            float screenWidth = (float) getResources().getDisplayMetrics().widthPixels;
            float screenHeight = (float) getResources().getDisplayMetrics().heightPixels;
            Rect r = new Rect();
            this.textData.textPaint.getTextBounds(TextData.defaultMessage, 0, TextData.defaultMessage.length(), r);
            this.textData.xPos = (screenWidth / 2.0f) - ((float) (r.width() / 2));
            this.textData.yPos = (screenHeight / 2.0f) - ((float) (r.height() / 2));
            Log.e(TAG, "textData==null");
            this.editText.setText("");
            this.textView.setText(getString(R.string.preview_text));
        } else {
            if (!this.textData.message.equals(TextData.defaultMessage)) {
                this.editText.setText(this.textData.message, BufferType.EDITABLE);
            }
            Log.e(TAG, this.textData.message);
            this.textView.setTextColor(this.textData.textPaint.getColor());
            this.textView.setText(this.textData.message);
            if (this.textData.getFontPath() != null) {
                Typeface typeFace = FontCache.get(this.activity, this.textData.getFontPath());
                if (typeFace != null) {
                    this.textView.setTypeface(typeFace);
                }
            }
        }
        Log.e(TAG, this.textView.getText().toString());
        Log.e(TAG, this.textData.message);
        Log.e(TAG, this.editText.getText().toString());
        GridView gridView = (GridView) fragmentView.findViewById(R.id.gridview_font);
        this.customGridAdapter = new GridViewAdapter(this.activity, R.layout.row_grid, this.fontPathList);
        gridView.setAdapter(this.customGridAdapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               // Typeface typeFace = FontCache.get(FontFragment.this.activity, FontFragment.this.fontPathList[position]);
                Typeface typeFace =Typeface.createFromAsset(getActivity().getAssets(), FontFragment.this.fontPathList[position]);
                if (typeFace != null) {
                    FontFragment.this.textView.setTypeface(typeFace);
                }
                FontFragment.this.textData.setTextFont(FontFragment.this.fontPathList[position], FontFragment.this.activity);
            }
        });

        fragmentView.findViewById(R.id.button_text_color).setOnClickListener(this);
        fragmentView.findViewById(R.id.button_font_ok).setOnClickListener(this);
        return fragmentView;
    }

    public void setFontChoosedListener(FontChoosedListener l) {
        this.fontChoosedListener = l;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.activity = getActivity();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.textview_font) {
            this.editText.requestFocusFromTouch();
            ((InputMethodManager) this.activity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(this.editText, 0);
            String message = this.textView.getText().toString();
            if (message.compareToIgnoreCase(TextData.defaultMessage) != 0) {
                this.editText.setText(message);
                this.editText.setSelection(this.editText.getText().length());
            } else {
                this.editText.setText("");
            }
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    FontFragment.this.editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, 0.0f, 0.0f, 0));
                    FontFragment.this.editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, 0.0f, 0.0f, 0));
                    FontFragment.this.editText.setSelection(FontFragment.this.editText.getText().length());
                }
            }, 200);
        } else if (id == R.id.button_font_ok) {
            String newMessage = this.textView.getText().toString();
            if (newMessage.compareToIgnoreCase(TextData.defaultMessage) == 0 || newMessage.length() == 0) {
                if (this.activity == null) {
                    this.activity = getActivity();
                }
                Toast msg = Toast.makeText(this.activity, getString(R.string.canvas_text_enter_text), Toast.LENGTH_SHORT);
                msg.setGravity(17, msg.getXOffset() / 2, msg.getYOffset() / 2);
                msg.show();
                return;
            }
            if (newMessage.length() == 0) {
                this.textData.message = TextData.defaultMessage;
            } else {
                this.textData.message = newMessage;
            }
            this.editText.setText("");
            this.textView.setText("");
            ((InputMethodManager) this.activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
            if (this.fontChoosedListener != null) {
                this.fontChoosedListener.onOk(this.textData);
            } else {
                Toast.makeText(getActivity(), "Null", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.button_text_color) {
            try {
                ColorPickerDialogBuilder.with(getActivity()).setTitle("Choose Color").initialColor(this.textView.getCurrentTextColor()).wheelType(WHEEL_TYPE.FLOWER).density(12).setOnColorSelectedListener(new OnColorSelectedListener() {
                    public void onColorSelected(int selectedColor) {
                    }
                }).setPositiveButton((CharSequence) "ok", (ColorPickerClickListener) new ColorPickerClickListener() {
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        FontFragment.this.textView.setTextColor(selectedColor);
                        FontFragment.this.textData.textPaint.setColor(selectedColor);
                    }
                }).setNegativeButton((CharSequence) "cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).build().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onDestroy() {
        if (this.customGridAdapter != null) {
            if (this.customGridAdapter.typeFaceArray != null) {
                int length = this.customGridAdapter.typeFaceArray.length;
                for (int i = 0; i < length; i++) {
                    this.customGridAdapter.typeFaceArray[i] = null;
                }
            }
            this.customGridAdapter.typeFaceArray = null;
        }
        super.onDestroy();
    }
}
