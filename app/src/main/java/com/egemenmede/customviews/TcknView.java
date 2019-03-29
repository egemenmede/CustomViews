package com.egemenmede.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * TCKNView
 *
 * @author Egemen Mede
 * @version 1.0.0
 * @since  27.03.2019
 */
public class TcknView extends TextInputLayout {

    public static final String TAG = TcknView.class.getSimpleName();

    /**
    TC Kimlik No Doğrulama Algoritması

    Kural-1: Tüm karakterleri rakam olmalıdır.
    Kural-2: TC Kimlik numarası 11 basamaktan oluşmalıdır.
    Kural-3: İlk hanesi 0 olamaz.
    Kural-4: İlk 9 basamak arasındaki algoritma, 10. basamağı vermelidir.
    İşlem: 1. 3. 5. 7. ve 9. hanelerin toplamının 7 katından, 2. 4. 6. ve 8. hanelerin toplamı çıkartıldığında, elde edilen sonucun 10′a bölümünden kalan, yani Mod10′u bize 10. haneyi verir.
    Kural-5: İlk 10 basamak arasındaki algoritma, 11. basamağı vermelidir.
    İşlem: 1. 2. 3. 4. 5. 6. 7. 8. 9. ve 10. hanelerin toplamından elde edilen sonucun 10′a bölümünden (Mod 10) kalan, bize 11. haneyi verir.
    */

    private static final Integer CHK_COUNT = 10;
    private static final Integer CHR_COUNT = 11;
    AppCompatEditText eText;
    private Boolean visibleErrorMessege;
    private String defaultErrorMessege;

    public TcknView(Context context) {
        super(context);
        initComponent(context);
    }

    public TcknView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TcknView, 0, R.style.DefaultTcknStyle);

        visibleErrorMessege = typedArray.getBoolean(R.styleable.TcknView_visibleErrorMessege, true);
        defaultErrorMessege = typedArray.getString(R.styleable.TcknView_defaultErrorMessege);
        Log.i(TAG, "visibleErrorMessege:" + visibleErrorMessege);
        Log.i(TAG, "defaultErrorMessege:" + defaultErrorMessege);

        typedArray.recycle();
        initComponent(context);
    }

    /**
     * Komponentin başlangıç noktasıdır. Kural-1 ve Kural-2 burada tanımlanmıştır.
     * Kural-1: Tüm karakterleri rakam olmalıdır.
     * Kural-2: TC Kimlik numarası 11 basamaktan oluşmalıdır.
     */
    public void initComponent(Context context){
        eText = new AppCompatEditText(context);
        eText.setInputType(InputType.TYPE_CLASS_NUMBER);
        eText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(CHR_COUNT) });
        addView(eText);
    }

    public void checkTextChangedListener(TextWatcher watcher) {
        eText.addTextChangedListener(watcher);
    }

    /**
     *
     * TCKN Algoritmasına göre 3 numaralı kuralı kontrol eden metottur. Koşul sağlanıyorsa true, sağlanmıyorsa false değeri döndürür.
     * Kural-3: İlk hanesi 0 olamaz.
     *
     * @param val
     * @return Boolean
     */
    public Boolean getFirstValueZeroStatus(String val){
        List<String> tcknCharList = getTcknCharList(val);
        if (!getTcknCharList(val).isEmpty()){
            return tcknCharList.get(0).equals("0");
        }
        return false;
    }

    /**
     * TCKN Algoritmasına göre 4 numaralı kuralı kontrol ederek koşul sağlanıyorsa true, sağlanmıyorsa false değeri döndürür.
     * Kural-4: İlk 9 basamak arasındaki algoritma, 10. basamağı vermelidir.
     *
     * @param val
     * @return Boolean
     */
    public Boolean getRuleFourStatus(String val){
        List<String> tcknCharList = getTcknCharList(val);
        if (tcknCharList.size() < CHK_COUNT) return false;

        return getFirstNineTotal(tcknCharList).equals(tcknCharList.get(CHK_COUNT-1));
    }

    /**
     * TCKN Algoritmasına göre 5 numaralı kuralı kontrol ederek koşul sağlanıyorsa true, sağlanmıyorsa false değeri döndürür.
     * Kural-5: İlk 10 basamak arasındaki algoritma, 11. basamağı vermelidir.
     *
     * @param val
     * @return Boolean
     */
    public Boolean getRuleFiveStatus(String val){
        List<String> tcknCharList = getTcknCharList(val);
        if (tcknCharList.size() != CHR_COUNT) return false;

        return String.valueOf(tcknCharList.get(CHK_COUNT)).equals(String.valueOf(getTopTenTotal(tcknCharList) % 10));
    }

    public Integer getChrCount(){
        return CHR_COUNT;
    }

    public Integer getChkCount(){
        return CHK_COUNT;
    }

    /**
     * Kullanıcı arayüzünde girilmiş olan TCKN'nin List<String> tipine dönüştürür. Utility metodudur.
     *
     * @param val
     * @return List<String>
     */
    public List<String> getTcknCharList(String val){
        List<String> tcknCharList = new ArrayList<>();
        for (int i = 0; i < val.length(); i++) {
            tcknCharList.add(String.valueOf(val.charAt(i)));
        }
        return tcknCharList;
    }

    /**
     * TCKN Algoritmasına göre ilk 10 rakamın toplamını int olarak geri döndürür. Utility metodudur.
     *
     * @param tcknCharList
     * @return int
     */
    private int getTopTenTotal(List<String> tcknCharList){
        int total = 0;
        for (int i = 0; i < CHK_COUNT; i++) {
            total += Integer.valueOf(tcknCharList.get(i));
        }

        return total;
    }

    /**
     * TCKN Algoritmasına göre ilk 9 rakamın toplamını String olarak geri döndürür. Utility metodudur.
     *
     * @param tcknCharList
     * @return String
     */
    private String getFirstNineTotal(List<String> tcknCharList){
        int singleNumberTotal = 0;
        int doubleNumberTotal = 0;

        for (int i = 0; i < 9; i++) {
            if (i % 2 == 0) {
                singleNumberTotal += Integer.valueOf(tcknCharList.get(i));
            }else{ // 2,4,6,8
                doubleNumberTotal += Integer.valueOf(tcknCharList.get(i));
            }
        }

        return String.valueOf(((singleNumberTotal*7)-doubleNumberTotal) % 10);
    }

    /**
     * Kalan TCKN karakter sayısını hesaplar.
     *
     * @param val
     * @return int
     */
    public int getNumberOfChrs(String val){
        List<String> tcknCharList = getTcknCharList(val);
        return CHR_COUNT-tcknCharList.size();
    }

    public boolean isVisibleErrorMessege() {
        return visibleErrorMessege;
    }

    public void setVisibleErrorMessege(boolean visibleErrorMessege) {
        this.visibleErrorMessege = visibleErrorMessege;
    }

    public String getDefaultErrorMessege() {
        return defaultErrorMessege;
    }

    public void setDefaultErrorMessege(String defaultErrorMessege) {
        this.defaultErrorMessege = defaultErrorMessege;
    }
}
