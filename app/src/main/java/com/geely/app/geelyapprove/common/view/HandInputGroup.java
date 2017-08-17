package com.geely.app.geelyapprove.common.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geely.app.orientalpearl.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Oliver on 2016/9/18.
 */
public class HandInputGroup extends LinearLayout {

    Button btnDelete;
    Button btnExpand;
    TextView tvGroupTitle;
    TextView v1;
    TextView v2;
    RelativeLayout user;
    LinearLayout btnBottom;
    TextView tvTopRight;
    private String drawable;
    private Callback callback;
    private Context context;
    private List<Holder> holders;
    private Boolean hasExpandableButton;
    private CharSequence title;
    private int groupBottomDrawableRes;
    private boolean isListViewExpanded;
    private RecyclerView lvContent;
    private MyViewAdapter adapter;
    private Integer index;
    private RelativeLayout relativeLayout;
    private boolean isopen;
    private boolean hasDelete;
    private AlertDialog.Builder alert;
    private String oldBarcode;
    private ImageView photo;


    public HandInputGroup(Context context) {
        super(context);
        this.context = context;
        setHandGroup(context, null);
    }

    public HandInputGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setHandGroup(context, attrs);

    }

    public HandInputGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setHandGroup(context, attrs);
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public Boolean getHasExpandableButton() {
        return hasExpandableButton;
    }

    public void setHasExpandableButton(Boolean hasExpandableButton) {
        this.hasExpandableButton = hasExpandableButton;
        btnExpand.setVisibility(hasExpandableButton ? VISIBLE : GONE);
        if (hasExpandableButton && !btnExpand.hasOnClickListeners()) {
            btnExpand.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    lvContent.setVisibility(isListViewExpanded ? VISIBLE : GONE);
                    v.setBackgroundResource(!isListViewExpanded ? R.mipmap.ic_open : R.mipmap.ic_close);
                    isListViewExpanded = !isListViewExpanded;
                }
            });
        }
    }


    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
        tvGroupTitle.setText(title);
    }

    public int getGroupBottomDrawableRes() {
        return groupBottomDrawableRes;
    }

    //设置添加条目是否显示
    public void setGroupBottomDrawableRes(Integer groupBottomDrawableRes, OnClickListener listener) {
        if (groupBottomDrawableRes != null && listener != null) {
            this.groupBottomDrawableRes = groupBottomDrawableRes;
            btnBottom.setVisibility(VISIBLE);
            btnBottom.setOnClickListener(listener);
        } else {
            btnBottom.setVisibility(GONE);
        }
    }


    public void setrl(boolean rl){
        if (rl){
            relativeLayout.setVisibility(GONE);
            user.setVisibility(VISIBLE);

        }
    }

    private void setHandGroup(Context context, AttributeSet attrs) {
        if (context == null) return;
        View view = inflate(context, R.layout.layout_hand_input_group, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnExpand = (Button) view.findViewById(R.id.btn_expand);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rlayout_title_bar);
        user = (RelativeLayout) view.findViewById(R.id.user);
        v1 = (TextView) view.findViewById(R.id.tv1);
        v2 = (TextView) view.findViewById(R.id.tv2);
        photo = (ImageView) view.findViewById(R.id.item_user_iv);
        lvContent = (RecyclerView) view.findViewById(R.id.list_item_content);
        lvContent.setLayoutManager(new LinearLayoutManager(context, VERTICAL, false));
        lvContent.setItemAnimator(new DefaultItemAnimator());
        tvGroupTitle = (TextView) view.findViewById(R.id.tv_item_title_content);
        btnBottom = (LinearLayout) view.findViewById(R.id.btn_bottom_hand_group);
        tvTopRight = (TextView) view.findViewById(R.id.tv_top_right_value);
        if (attrs == null) {
            setTitle("");
            setHasExpandableButton(true);
        } else {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HandInputGroup);
            setoldBarcode(array.getString(R.styleable.HandInputGroup_oldBarCode));
            setdrawable(array.getString(R.styleable.HandInputGroup_drawable));
            Bitmap bitmap = null;
            byte[] bitmapArray;
            bitmapArray = Base64.decode(array.getString(R.styleable.HandInputGroup_drawable), Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            BitmapDrawable bd = new BitmapDrawable(bitmap);
            Drawable d = (Drawable) bd;
            photo.setBackgroundDrawable(d);
            sethasDelete(array.getBoolean(R.styleable.HandInputGroup_hasDeleteButton,false));
            setrl(array.getBoolean(R.styleable.HandInputGroup_rl,false));
            setisopen(array.getBoolean(R.styleable.HandInputGroup_isopen,false));
            setTitle(array.getText(R.styleable.HandInputGroup_groupTitle));
            setHasExpandableButton(array.getBoolean(R.styleable.HandInputGroup_hasExpandableButton, true));
            array.recycle();
        }
        addView(view);
    }

    public void setTopRightValue(String topRightValue) {
        if(topRightValue!=null){
            tvTopRight.setVisibility(VISIBLE);
            btnExpand.setVisibility(INVISIBLE);
            tvTopRight.setText(topRightValue);
        }else {
            tvTopRight.setVisibility(INVISIBLE);
            btnExpand.setVisibility(VISIBLE);
        }
    }

    public void setv1(String v1){
        this.v1.setText(v1);
    }

    public void setv2(String v2){
        this.v2.setText(v2);
    }

    public void setHolders(List<Holder> holders) {
        this.holders = holders;
        if (holders == null)
            this.holders = new ArrayList<>();
        adapter = new MyViewAdapter();
        lvContent.setAdapter(adapter);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private <T extends TextView> void checkHint(T view, Holder holder) {
        if (holder != null && holder.getDispayValue() != null && holder.getDispayValue().startsWith("/")) {
            view.setHint(holder.getDispayValue().substring(1));
        } else {
            view.setText(holder.getDispayValue());
        }
        view.setTextColor(holder.getColor());
        if (holder.getDrawableRight() != -1) {
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, holder.getDrawableRight(), 0);
        }
    }

    public void setisopen(boolean isopen) {
        this.isopen = isopen;
        lvContent.setVisibility(isopen ? VISIBLE : GONE);
        btnExpand.setBackgroundResource(!isopen?R.mipmap.ic_open : R.mipmap.ic_close);
        isListViewExpanded = !isopen;
    }

    public boolean getisopen(){
        return isopen;
    }

    public void sethasDelete(boolean hasDelete) {
        this.hasDelete = hasDelete;
        btnDelete.setVisibility(hasDelete?VISIBLE:GONE);
        if (hasDelete){
            btnDelete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert = new AlertDialog.Builder(context);
                    alert.setMessage("是否确认删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            callback.removeItemCallback(index,HandInputGroup.this);
                        }
                    }).setNegativeButton("取消",null);
                    alert.create();
                    alert.show();

                }
            });
        }

    }

    public HandInputGroup setoldBarcode(String oldBarcode) {
        this.oldBarcode = oldBarcode;
        return this;
    }

    public String getOldBarcode(){
        return this.oldBarcode;
    }

    //base64转换为图片并添加到imageview
    public void setdrawable(String drawable) {
        this.drawable = drawable;
        if (drawable != null && !drawable.isEmpty() && !drawable.equals("error")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Bitmap bitmap = null;
                byte[] bitmapArray = Base64.decode(drawable, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                if(bitmap != null){
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    float scaleHeight = (float) (670.00/height);
                    float scaleWidth = scaleHeight;
                    Matrix matrix=new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
                    Bitmap resizeBmp=Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
                    BitmapDrawable bd = new BitmapDrawable(resizeBmp);
                    Drawable d = (Drawable) bd;
                    this.photo.setBackground(d);
                }

            }
        }

    }

    public String getdrawable(){
        return this.drawable;
    }


    /**
     * <p>定义参数类型，决定着后面的布局类型</p>
     * SELECT 可选择类型，右边会有个箭头，点击后展示下拉列表
     * PICK 可选择类型，右边有箭头，点击之后跳转到人员列表界面
     * TEXTFILED 可编辑类型，默认显示hint属性为该项目的值
     * TEXT 不可编辑类型，默认显示黑体状态
     * DATE 日期类型，出现日期选择的按钮
     * BUTTONS 选择是否
     * FILES_UPLOAD 附件
     * DOUBLE 只能输入数字
     * SUB_LIST 关联单
     * EDIT_DOUBLE 与DOUBLE类似，但是中间多了一个textview
     */
    public enum VALUE_TYPE {
        SELECT, TEXT, DATE, BUTTONS, TEXTFILED, FILES_UPLOAD, DOUBLE,SUB_LIST,EDIT_DOUBLE,PICK
    }

    public interface Callback {
        void onClickItemContentSetter(Holder holder);
        boolean removeItemCallback(int index, HandInputGroup group);
        /**
         * @param main   组index
         * @param index  次index
         * @param holder 该holder
         */
        void onHolderTextChanged(int main, int index, Holder holder);
        void onHolderChangedOver(int main, int index,Holder holder);
        void removeGroups(int index, HandInputGroup group);
        void onBarCodeChanged(int main, int index, Holder holder, String barcode, SubListLayout.ActionType type, int id);
        void onDataChanged(Holder holder) throws ParseException;
        void FocusChange(Holder item, boolean hasFocus);
    }

    public static class Holder<T> {
        private View tvview;
        private String drawable;
        private String key;
        private String number;
        private Boolean hasIndicator;
        private Boolean hasLargeDivider;
        private boolean hasDelete;
        private boolean isopen;
        private boolean rl;
        private String dispayValue;
        private VALUE_TYPE type;
        private Boolean editable;
        private Integer imeType;
        private int color = Color.BLACK;
        private int drawableRight = -1;
        private String oldBarcode;
        private T value;
        private Object tag;

        public Object getTag() {
            return tag;
        }

        public Holder setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public View getTvview() {
            return tvview;
        }

        public void setTvview(View tvview) {
            this.tvview = tvview;
        }

        public boolean getRl(){
            return rl;
        }

        public void  setRl(boolean rl){
            this.rl = rl;
        }

        public void setDrawable(String drawable){
            this.drawable = drawable;
        }

        public String getDrawable() {
            return drawable;
        }

        public String getoldBarcode(){
            return this.oldBarcode;
        }

        public void setoldBarcode(String oldBarcode){
            this.oldBarcode = oldBarcode;
        }

        private void setIsopen(boolean isopen){
            this.isopen = isopen;
        }

        private boolean getisopen(){
            return this.isopen;
        }

        public void setHasDelete(boolean has){
            this.hasDelete = has;
        }

        public boolean gethasDelete(){
            return this.hasDelete;
        }

        public T getValue() {
            return value;
        }

        public Holder<T> setValue(T value) {
            this.value = value;
            return this;
        }

        public Holder(String key, Boolean hasIndicator, Boolean hasLargeDivider, String dispayValue, VALUE_TYPE type) {
            this.key = key;
            this.hasIndicator = hasIndicator;
            this.hasLargeDivider = hasLargeDivider;
            this.dispayValue = dispayValue;
            this.type = type;
            this.rl = false;
        }

        public int getDrawableRight() {
            return drawableRight;
        }

        public Holder setDrawableRight(int drawableRight) {
            this.drawableRight = drawableRight;
            return this;
        }

        public int getColor() {
            return color;
        }

        public Holder setColor(int color) {
            this.color = color;
            return this;
        }

        public Boolean getEditable() {
            return editable;
        }

        public Holder setEditable(Boolean editable) {
            this.editable = editable;
            return this;
        }

        public Integer getImeType() {
            return imeType;
        }

        public Holder setImeType(Integer imeType) {
            this.imeType = imeType;
            return this;
        }

        public Boolean isEditable() {
            return editable;
        }

        public String getRealValue() {
            if (dispayValue.trim().startsWith("/")) {
                return "";
            } else {
                return dispayValue;
            }

        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getNumber() {
            return number;
        }

        public Holder setNumber(String num) {
            this.number = num;
            return this;
        }

        public Boolean getHasIndicator() {
            return hasIndicator;
        }

        public void setHasIndicator(Boolean hasIndicator) {
            this.hasIndicator = hasIndicator;
        }

        public Boolean getHasLargeDivider() {
            return hasLargeDivider;
        }

        public void setHasLargeDivider(Boolean hasLargeDivider) {
            this.hasLargeDivider = hasLargeDivider;
        }

        public String getDispayValue() {
            return dispayValue;
        }

        public Holder setDispayValue(String dispayValue) {
            this.dispayValue = dispayValue;
            return this;
        }

        public VALUE_TYPE getType() {
            return type;
        }

        public void setType(VALUE_TYPE type) {
            this.type = type;
        }
    }

    public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.MyViewHolder> {

        public MyViewAdapter() {
            inflater = LayoutInflater.from(context);
        }

        LayoutInflater inflater;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = null;
            if (viewType == VALUE_TYPE.DATE.ordinal()) {
                holder = new MyViewHolder(inflater.inflate(R.layout.layout_hand_group_listitem_date, parent, false));
            } else if (viewType == VALUE_TYPE.TEXTFILED.ordinal()) {
                holder = new MyViewHolder(inflater.inflate(R.layout.layout_hand_group_listitem_textfiled, parent, false));
            } else if (viewType == VALUE_TYPE.FILES_UPLOAD.ordinal()) {
                FileChooserLayout convertView = new FileChooserLayout(context);
                holder = new MyViewHolder(convertView);
            } else if(viewType == VALUE_TYPE.DOUBLE.ordinal()){
                holder = new MyViewHolder(inflater.inflate(R.layout.list_item_hand_group_double, parent, false));
            }else if(viewType == VALUE_TYPE.SUB_LIST.ordinal()){
                holder = new MyViewHolder(inflater.inflate(R.layout.layout_sublist, parent, false));
            }else if(viewType == VALUE_TYPE.TEXT.ordinal()){
                holder = new MyViewHolder(inflater.inflate(R.layout.list_item_hand_group_text, null, false));
            }else if (viewType == VALUE_TYPE.BUTTONS.ordinal()){
                holder = new MyViewHolder(inflater.inflate(R.layout.layout_selector_radiobutton,parent,false));
            }else if (viewType == VALUE_TYPE.EDIT_DOUBLE.ordinal()){
                holder = new MyViewHolder(inflater.inflate(R.layout.list_item_hand_group_enit_double,parent,false));
            } else {
                holder = new MyViewHolder(inflater.inflate(R.layout.list_item_hand_group, parent, false));
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Holder item = holders.get(position);
            oldBarcode = item.getoldBarcode();
            if (holder.tvIndicator != null) {
                if (item.getHasIndicator() == null || !item.getHasIndicator()) {
                    holder.tvIndicator.setVisibility(INVISIBLE);
                } else if (item.getHasIndicator() != null && item.getHasIndicator()) {
                    holder.tvIndicator.setVisibility(VISIBLE);
                }
            }

            if (holder.tvKey != null) {
                if (item.getKey() != null) {
                    holder.tvKey.setText(item.getKey());
                }
            }
            if (item.getNumber() != null){
                holder.tvnum.setText(item.getNumber());
            }
            if (item.gethasDelete()){
                holder.btnDelete.setVisibility(VISIBLE);
            }
            if (item.getRl()){
                holder.user.setVisibility(item.getRl()?VISIBLE:GONE);
            }

            switch (item.getType()) {
                case TEXT:
                    TextView textView = (TextView) holder.tvValue;
                    textView.setCompoundDrawables(null, null, null, null);
                    textView.setText(item.getDispayValue());
                    textView.setTextColor(Color.BLACK);
                    textView.setEnabled(false);
                    checkHint(textView, item);
                    break;
                case PICK:
                case SELECT:
                    final EditText selectView = (EditText) holder.tvValue;
                    checkHint(selectView, item);
                    selectView.setFocusable(false);
                    holder.tvValue.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callback.onClickItemContentSetter(item);
                        }
                    });
                    break;
                case DATE:
                    TextView dateView = (TextView) holder.tvValue;
                    checkHint(dateView, item);
                    dateView.setFocusable(false);
                    holder.tvValue.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callback.onClickItemContentSetter(item);
                        }
                    });
                    break;
                case BUTTONS:
                    final RadioGroup radioGroup = (RadioGroup) holder.tvValue;
                    if (item.isEditable() != null && !item.isEditable()) {
                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                            radioGroup.getChildAt(i).setEnabled(false);
                        }
                    } else {
                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                            radioGroup.getChildAt(i).setEnabled(true);
                        }
                    }
                    RadioButton radioButtonmale = (RadioButton) radioGroup.findViewById(R.id.male);
                    radioGroup.check(item.getDispayValue().equals(radioButtonmale.getText())?R.id.male:R.id.female);
                    ((RadioGroup) holder.tvValue).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (checkedId == R.id.male){
                                RadioButton radioButtonmale = (RadioButton) radioGroup.findViewById(R.id.male);
                                item.setDispayValue(radioButtonmale.getText().toString());
                            }else if (checkedId == R.id.female){
                                RadioButton radioButtonfemale = (RadioButton) radioGroup.findViewById(R.id.female);
                                item.setDispayValue(radioButtonfemale.getText().toString());
                            }
                            callback.onClickItemContentSetter(item);
                        }
                    });
                    break;
                case DOUBLE:

                case TEXTFILED:
                    final EditText editText = (EditText) holder.tvValue;
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            item.setDispayValue(editText.getText().toString());
                            callback.onHolderTextChanged(index, holder.getAdapterPosition(), item);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    if (item.isEditable() != null && !item.isEditable()) {
                        editText.setFocusable(false);
                    } else {
                        editText.setFocusable(true);
                    }
                    if (item.getImeType() != null) {
                        editText.setInputType(item.getImeType());
                    }
                    checkHint(editText, item);
                    final TextView finalTvDivider = holder.tvDivider;
                    editText.setOnFocusChangeListener(new OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            callback.FocusChange(item,hasFocus);
                            if (!hasFocus){
                                callback.onHolderChangedOver(index, holder.getAdapterPosition(),item);
                            }
                            if (finalTvDivider != null && !hasFocus) {
                                finalTvDivider.setBackgroundColor(getResources().getColor(R.color.gray_dark));
                            } else if (finalTvDivider != null) {
                                finalTvDivider.setBackgroundColor(getResources().getColor(R.color.colorAccentLib));
                            }
                        }
                    });
                    break;
                case FILES_UPLOAD:
                    FileChooserLayout layout = (FileChooserLayout) holder.tvValue;
                    layout.setPaths((HashSet<Uri>)item.getValue());
                    layout.setListener((FileChooserLayout.DataChangeListener) item.getTag());
                    break;
                case SUB_LIST:
                    SubListLayout listLayout = (SubListLayout) holder.tvValue;
                    if(item.getValue()!=null){
                        Set<String> value = (Set<String>) item.getValue();
                        listLayout.setBarCode(value);
                        listLayout.setCallback(new SubListLayout.ResultCallback() {
                            @Override
                            public void onAction(String barcode, SubListLayout.ActionType type,int id) {
                                callback.onBarCodeChanged(index, holder.getAdapterPosition(), item,barcode,type,id);
                            }
                        });
                    }
                    if (item.getHasLargeDivider()){
                        final TextView TvDivider = holder.tvDivider;
                        TvDivider.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    break;
                case EDIT_DOUBLE:
                    final EditText editDouble = (EditText) holder.tvValue;
                    editDouble.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            item.setDispayValue(editDouble.getText().toString());
                            callback.onHolderTextChanged(index, holder.getAdapterPosition(), item);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    if (item.isEditable() != null && !item.isEditable()) {
                        editDouble.setFocusable(false);
                    } else {
                        editDouble.setFocusable(true);
                    }
                    if (item.getImeType() != null) {
                        editDouble.setInputType(item.getImeType());
                    }
                    checkHint(editDouble, item);
                    final TextView TvDivider = holder.tvDivider;
                    editDouble.setOnFocusChangeListener(new OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            callback.FocusChange(item,hasFocus);
                            if (TvDivider != null && !hasFocus) {
                                TvDivider.setBackgroundColor(getResources().getColor(R.color.gray_dark));
                            } else if (TvDivider != null) {
                                TvDivider.setBackgroundColor(getResources().getColor(R.color.colorAccentLib));
                            }
                        }
                    });

                    break;
            }
        }

        @Override
        public int getItemCount() {
            return holders.size();
        }

        @Override
        public int getItemViewType(int position) {
            return holders.get(position).getType().ordinal();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvKey;
            TextView tvDivider;
            TextView tvIndicator;
            View tvValue;
            RelativeLayout user;
            RadioButton rabtnY;
            RadioButton rabtnN;
            Button btnDelete;
            TextView tvnum;
            ImageView photo;

            public MyViewHolder(View view) {
                super(view);
                if (view instanceof FileChooserLayout) {
                    tvValue = view;
                    return;
                }
                photo = (ImageView) view.findViewById(R.id.item_user_iv);
                btnDelete = (Button) view.findViewById(R.id.btn_delete);
                rabtnY = (RadioButton) view.findViewById(R.id.male);
                rabtnN = (RadioButton) view.findViewById(R.id.female);
                tvKey = (TextView) view.findViewById(R.id.tv_title);
                tvnum = (TextView) view.findViewById(R.id.number);
                user = (RelativeLayout) view.findViewById(R.id.user);
                tvValue = view.findViewById(R.id.tv_value);
                tvIndicator = (TextView) view.findViewById(R.id.indicator_must_have);
                tvDivider = (TextView) view.findViewById(R.id.divider);
            }
        }
    }
}
