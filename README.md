# CustomViews - TcknView

## TcknView
Android için TCKN (TC Kimlik No) kontrolü yapan bileşen.

## TC Kimlik No Doğrulama Algoritması

- Kural-1: Tüm karakterleri rakam olmalıdır.
- Kural-2: TC Kimlik numarası 11 basamaktan oluşmalıdır.
- Kural-3: İlk hanesi 0 olamaz.
- Kural-4: İlk 9 basamak arasındaki algoritma, 10. basmağı vermelidir.
- İşlem: 1. 3. 5. 7. ve 9. hanelerin toplamının 7 katından, 2. 4. 6. ve 8. hanelerin toplamı çıkartıldığında, elde edilen sonucun 10′a bölümünden kalan, yani Mod10′u bize 10. haneyi verir.
- Kural-5: İlk 10 basamak arasındaki algoritma, 11. basamağı vermelidir.
- İşlem: 1. 2. 3. 4. 5. 6. 7. 8. 9. ve 10. hanelerin toplamından elde edilen sonucun 10′a bölümünden (Mod 10) kalan, bize 11. haneyi verir.

## Kullanımı

### Xml
```xml
<com.egemenmede.customviews.TcknView
        android:id="@+id/tcknView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/tckn_hint" />
```

### Java
```xml
tcknView = findViewById(R.id.tcknView);
tcknView.checkTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // beforeTextChanged
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String txtInput = charSequence.toString();

        if (tcknView.getFirstValueZeroStatus(txtInput) && tcknView.isVisibleErrorMessege()){
                //DEFAULT: tcknView.setError(tcknView.getDefaultErrorMessege());
                tcknView.setError(getString(R.string.tckn_rule3_messege));
        }else{
            if (tcknView.getNumberOfChrs(txtInput) != 0) {
                tcknView.setError(String.format(getString(R.string.tckn_number_of_chrs), tcknView.getNumberOfChrs(txtInput)));
            }
            if (tcknView.getNumberOfChrs(txtInput) == tcknView.getChrCount() || tcknView.getNumberOfChrs(txtInput) == 0) {
                tcknView.setErrorEnabled(false);
            }
            if (txtInput.length() >= tcknView.getChkCount() && tcknView.getRuleFourStatus(txtInput).equals(false) && tcknView.isVisibleErrorMessege()){
                //DEFAULT: tcknView.setError(tcknView.getDefaultErrorMessege());
                tcknView.setError(getString(R.string.tckn_rule4_messege));
            }
            if (txtInput.length() >= tcknView.getChrCount() && tcknView.getRuleFiveStatus(txtInput).equals(false) && tcknView.isVisibleErrorMessege()){
                //DEFAULT: tcknView.setError(tcknView.getDefaultErrorMessege());
                tcknView.setError(getString(R.string.tckn_rule5_messege));
            }
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {
        // afterTextChanged
    }
});
```

İstenirse XML içerisinden ya da kod içerisinde 2 özelliği üzerinde parametrik olarak değişiklik yapılabilir.

```xml
tckn:visibleErrorMessege="true"
tckn:defaultErrorMessege="Default Message"
```

İsterseniz TC Kimlik No Doğrulama Algoritması içerisindeki her bir hata şekli için ayrı ayrı mesajlar verebildiğiniz gibi, hepsi için tek bir mesaj da verebilirsiniz.

Bunun için `defaultErrorMessege` parametresine istediğiniz mesajı verebilirsiniz. Oluşacak hataların gösterilmesi ya da gösterilmemesini istiyorsanız `visibleErrorMessege`parametresinin değerini `true` veya `false` olarak verebilirsiniz.

XML içerisinde hiç bir parametre belirtilmemişse varsayılan değerleri `true` ve `Default Message` dır.

## string.xml
```xml
<resources>
    <string name="app_name">TcknView</string>
    <string name="tckn_default_error_messege">Geçersiz TCKN Numarası</string>
    <string name="tckn_hint">TC Kimlik Numarası</string>
    <string name="tckn_rule3_messege">İlk hanesi 0 olamaz.</string>
    <string name="tckn_rule2_messege">TC Kimlik numarası 11 basamaktan oluşmalıdır.</string>
    <string name="tckn_rule4_messege">İlk 9 basamak arasındaki algoritma, 10. basamağı vermelidir.</string>
    <string name="tckn_rule5_messege">İlk 10 basamak arasındaki algoritma, 11. basamağı vermelidir.</string>
    <string name="tckn_number_of_chrs">Kalan karakter sayısı: %1$d</string>
</resources>
```

Her hata türü için farklı bir mesaj vermek isterseniz yukarıdaki gibi hata mesajlarını değiştirebilirsiniz.

## style.xml
```xml
<style name="DefaultTcknStyle">
    <item name="visibleErrorMessege">true</item>
    <item name="defaultErrorMessege">@string/tckn_default_error_messege</item>
</style>
```

XML içerisinde her hangi bir parametre belirtmediğinizde alınacak varsayılan değerleri de buradan değiştirebilirsiniz.

## Kurulum
Bileşeni henüz bir Android library'si haline çevirmediğim için manuel kurulum yapılmaldır.

```res>values>``` altındaki
+ ```attrs.xml```
+ ```string.xml```
+ ```style.xml``` ve root altındaki
+ ```TcknView.java```

dosyalarını projenizin ilgili yerlerine kopyalayın.
