# BilgisayarAraProjesi
Yıldız Teknik Üniversitesi Bilgisayar Ara Projesi

Jupyter Notebook Klasörü

Paylaşılan .ipynb uzantılı dosya Anaconda Navigator Jupyter Notebook kullanılarak çalıştırılır. Bu işlemlerin sonucunda melbourne_prices_model.pickle
ve columns.json isimli iki dosya oluşturulur. 

PyCharm Klasörü

Flask serverı hazır hale getirmek için çalıştıracağımız klasör oluşturulur. Klasörün içinde model ve server adlı iki klasör daha bulunmalıdır. Model 
klasörünün içine code klasörü altındaki Jupyter Notebook klasöründeki Melbourne.ipynb ve bu dosyadan elde etiğimiz melbourne_prices_model.pickle ve columns.json
dosyaları eklenir. Server klasörünün içinde artifacts adlı bir klasör ve code klasörü altındaki PyCharm klasöründe bulunan kodlar bulunmalıdır. artifacts
klasörünün içinde ise melbourne_prices_model.pickle ve columns.json dosyaları bulunmalıdır. Bu haliyle dosyamız PyCharm kullanılarak açılıp çalıştırmaya hazır
olacaktır.

MelbourneFlask
    ->model
	-columns.json
	-Melbourne.ipynb
	-melbourne_prices_model.pickle
    ->server
	->artifacts
	    -columns.json
	    -melbourne_prices_model.pickle
	-Server.py
	-util.py


Android Klasörü

Android uygulaması için yazılmış olan kaynak kodlar activity_main.xml ve MainActivity.java kodları code klasörü altındaki Android klasöründe verilmiştir.

İlk olarak Jupyter Notebook kullanılarak Melbourne.ipynb dosyası çalıştırılır. Ardından oluşan .pickle ve .json uzantılı iki dosya daha kullanılarak PyCharm Klasörü
altında verilen bilgiler doğrultusunda dosya hazırlanır. Hazırlanan dosya PyCharm kullanılarak açılır. Server.py uzantılı dosya çalıştırılır. Android kısmında
MainActivity.java dosyası çalıştırılır. Açılan emülator ekranında gerekli parametreler girilerek kullanıcıya tahmin edilen değer döndürülür.
