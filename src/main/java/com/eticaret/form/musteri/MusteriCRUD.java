package com.eticaret.form.musteri;

import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.eticaret.staj.HibernateUtil;
import com.eticaret.staj.Musteri;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalSplitPanel;

public class MusteriCRUD extends VerticalSplitPanel {
	private VerticalSplitPanel verticalPanel;
	private AbsoluteLayout absLayoutSearch;
	private Label lblEkranSearch;
	private TextField txtMusteriNoSearch;
	private TextField txtTcNoSearch;
	private TextField txtAdSearch;
	private TextField txtSoyadSearch;
	private TextField txtTelNoSearch;
	private ComboBox cmbIlSearch;
	private ComboBox cmbIlceSearch;
	private ComboBox cmbMahalleSearch;
	private Button btnAraSearch;
	private Button btnYeniSearch;

	private AbsoluteLayout absLayoutRecord;
	private Label lblEkranRecord;
	private TextField txtMusteriNoRecord;
	private TextField txtTcNoRecord;
	private TextField txtAdRecord;
	private TextField txtSoyadRecord;
	private TextField txtTelNoRecord;
	private ComboBox cmbIlRecord;
	private ComboBox cmbIlceRecord;
	private ComboBox cmbMahalleRecord;
	private Button btnKaydetRecord;
	private Button btnSilRecord;
	private Button btnYeniRecord;
	private Button btnDeneme;
	private Table table;

	public MusteriCRUD() {
		setFirstComponent(buildForm());
		setSecondComponent(buildRecord());
		setSplitPosition(75, Unit.PERCENTAGE);
		setLocked(true); // Çubuğun hareket etmesi engelleniyor
		setHeight("135%");
	}

	private VerticalSplitPanel buildForm() {
		verticalPanel = new VerticalSplitPanel();
		verticalPanel.setFirstComponent(buildSearch());
		verticalPanel.setSecondComponent(buildTable());
		verticalPanel.setSplitPosition(32, Unit.PERCENTAGE);
		verticalPanel.setLocked(true);
		return verticalPanel;
	}

	private AbsoluteLayout buildSearch() {
		absLayoutSearch = new AbsoluteLayout();

		lblEkranSearch = new Label();
		lblEkranSearch.setCaption("Müşteri Arama Ekranı");
		absLayoutSearch.addComponent(lblEkranSearch, "left: 20px; top: 20px;");

		txtMusteriNoSearch = new TextField("Müşteri Numarası");
		absLayoutSearch.addComponent(txtMusteriNoSearch, "left: 20px; top: 50px;");

		txtTcNoSearch = new TextField("T.C. Kimlik Numarası");
		absLayoutSearch.addComponent(txtTcNoSearch, "left: 320px; top: 50px;");

		txtAdSearch = new TextField("Ad");
		absLayoutSearch.addComponent(txtAdSearch, "left: 620px; top: 50px;");

		txtSoyadSearch = new TextField("Soyad");
		absLayoutSearch.addComponent(txtSoyadSearch, "left: 920px; top: 50px;");

		cmbIlSearch = new ComboBox("İl");
		absLayoutSearch.addComponent(cmbIlSearch, "left: 20px; top: 100px;");

		cmbIlceSearch = new ComboBox("İlçe");
		absLayoutSearch.addComponent(cmbIlceSearch, "left: 320px; top: 100px;");

		cmbMahalleSearch = new ComboBox("Mahalle");
		absLayoutSearch.addComponent(cmbMahalleSearch,"left: 620px; top: 100px;");

		txtTelNoSearch = new TextField("Telefon Numarası");
		absLayoutSearch.addComponent(txtTelNoSearch, "left: 920px; top: 100px;");

		btnYeniSearch = new Button("Yeni");
		btnYeniSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				txtMusteriNoSearch.setValue("");
				txtTcNoSearch.setValue("");
				txtAdSearch.setValue("");
				txtSoyadSearch.setValue("");
				cmbIlSearch.setValue(null);
				cmbIlceSearch.setValue(null);
				cmbMahalleSearch.setValue(null);
				txtTelNoSearch.setValue("");
			}
		});
		absLayoutSearch.addComponent(btnYeniSearch, "left: 20px; top: 135px;");

		btnDeneme = new Button();
		absLayoutSearch.addComponent(btnDeneme, "left: 30px; top: 235px;");

		btnAraSearch = new Button();
		btnAraSearch.setCaption("Ara");
		btnAraSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				searchInDatabase(txtMusteriNoSearch.getValue(),
						txtTcNoSearch.getValue(), txtAdSearch.getValue(),
						txtSoyadSearch.getValue(), txtTelNoSearch.getValue());
				Notification.show("Arama işlemi gerçekleştirildi.");
			}
		});
		absLayoutSearch.addComponent(btnAraSearch, "left: 80px; top: 135px;");

		return absLayoutSearch;
	}

	private void searchInDatabase(String musteriNo, String TcNo, String ad,
			String soyad, String telNo) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;
		table.removeAllItems();
		try {
			tx = session.beginTransaction();
			List employees = session.createQuery("FROM Musteri").list();

			for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
				Musteri employee = (Musteri) iterator.next();
				if ((musteriNo.equals(employee.getMusteriNo()) || musteriNo
						.equals(""))
						&& (TcNo.equals(employee.getTcNo()) || TcNo.equals(""))
						&& (ad.equals(employee.getAd()) || ad.equals(""))
						&& (soyad.equals(employee.getSoyad()) || soyad
								.equals(""))
						&& (telNo.equals(employee.getTelNo()) || telNo
								.equals(""))) {

					table.addItem(
							new Object[] { employee.getMusteriNo(),
									employee.getTcNo(), employee.getAd(),
									employee.getSoyad(), null, null, null,
									employee.getTelNo() }, employee.getId());
				}

				else if ((musteriNo.equals("")) && (TcNo.equals(""))
						&& (ad.equals("")) && (soyad.equals(""))
						&& (telNo.equals(""))) {
					table.addItem(
							new Object[] { employee.getMusteriNo(),
									employee.getTcNo(), employee.getAd(),
									employee.getSoyad(), null, null, null,
									employee.getTelNo() }, employee.getId());
				}

			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
		} finally {
			session.close();
		}

	}

	private Table buildTable() {
		table = new Table();

		table.addContainerProperty("Müşteri Numarası", String.class, null);
		table.addContainerProperty("TC Kimlik No", String.class, null);
		table.addContainerProperty("Ad", String.class, null);
		table.addContainerProperty("Soyad", String.class, null);
		table.addContainerProperty("İl", String.class, null);
		table.addContainerProperty("İlçe", String.class, null);
		table.addContainerProperty("Mahalle", String.class, null);
		table.addContainerProperty("Telefon Numarası", String.class, null);
		table.setImmediate(true);
		table.setSelectable(true);

		table.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null)
					selectedTable((Long) event.getProperty().getValue());
			}
		});
		table.setSizeFull();
		return table;
	}

	private AbsoluteLayout buildRecord() {
		absLayoutRecord = new AbsoluteLayout();

		lblEkranRecord = new Label("Müşteri Kayıt Ekranı");
		absLayoutRecord.addComponent(lblEkranRecord, "left: 20px; top: 20px;");

		txtMusteriNoRecord = new TextField("Müşteri Numarası");
		absLayoutRecord.addComponent(txtMusteriNoRecord,"left: 20px; top: 50px;");

		txtTcNoRecord = new TextField("T.C. Kimlik Numarası");
		absLayoutRecord.addComponent(txtTcNoRecord, "left: 320px; top: 50px;");

		txtAdRecord = new TextField("Ad");
		absLayoutRecord.addComponent(txtAdRecord, "left: 620px; top: 50px;");

		txtSoyadRecord = new TextField("Soyad");
		absLayoutRecord.addComponent(txtSoyadRecord, "left: 920px; top: 50px;");

		cmbIlRecord = new ComboBox("İl");
		absLayoutRecord.addComponent(cmbIlRecord, "left: 20px; top: 100px;");

		cmbIlceRecord = new ComboBox("İlçe");
		absLayoutRecord.addComponent(cmbIlceRecord, "left: 320px; top: 100px;");

		cmbMahalleRecord = new ComboBox("Mahalle");
		absLayoutRecord.addComponent(cmbMahalleRecord,"left: 620px; top: 100px;");

		txtTelNoRecord = new TextField("Telefon Numarası");
		absLayoutRecord.addComponent(txtTelNoRecord, "left: 920px; top: 100px;");

		btnYeniRecord = new Button("Yeni");
		btnYeniRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				txtMusteriNoRecord.setValue("");
				txtTcNoRecord.setValue("");
				txtAdRecord.setValue("");
				txtSoyadRecord.setValue("");
				cmbIlRecord.setValue(null);
				cmbIlceRecord.setValue(null);
				cmbMahalleRecord.setValue(null);
				txtTelNoRecord.setValue("");
			}
		});
		absLayoutRecord.addComponent(btnYeniRecord, "left: 20px; top: 135px;");

		btnKaydetRecord = new Button("Kaydet");
		btnKaydetRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				addEmployee(txtMusteriNoRecord.getValue(),
						txtTcNoRecord.getValue(), txtAdRecord.getValue(),
						txtSoyadRecord.getValue(), txtTelNoRecord.getValue());
				Notification.show("Kaydetme işlemi gerçekleştirildi.");
			}
		});
		absLayoutRecord.addComponent(btnKaydetRecord, "left: 80px; top: 135px");

		btnSilRecord = new Button("Sil");
		btnSilRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				deleteEmployee(txtMusteriNoRecord.getValue(),
						txtTcNoRecord.getValue(), txtAdRecord.getValue(),
						txtSoyadRecord.getValue(), txtTelNoRecord.getValue());
				Notification.show("Silme işlemi gerçekleştirildi.");
			}
		});
		absLayoutRecord.addComponent(btnSilRecord, "left: 150px; top: 135px;");

		return absLayoutRecord;
	}

	private void addEmployee(String musteriNo, String TcNo, String ad,
			String soyad, String telNo) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Musteri musteri = new Musteri();
			musteri.setMusteriNo(musteriNo);
			musteri.setTcNo(TcNo);
			musteri.setAd(ad);
			musteri.setSoyad(soyad);
			musteri.setIl(null);
			musteri.setIlce(null);
			musteri.setMahalle(null);
			musteri.setTelNo(telNo);

			session.save(musteri);
			session.flush();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
		} finally {
			session.close();
		}
	}

	private void deleteEmployee(String musteriNo, String TcNo, String ad,
			String soyad, String telNo) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Musteri musteri = new Musteri();
			musteri.setMusteriNo(musteriNo);
			musteri.setTcNo(TcNo);
			musteri.setAd(ad);
			musteri.setSoyad(soyad);
			musteri.setTelNo(telNo);

			List employees = session.createQuery("FROM Musteri").list();
			for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
				Musteri employee = (Musteri) iterator.next();
				if (musteriNo.equals(employee.getMusteriNo())
						&& TcNo.equals(employee.getTcNo())
						&& ad.equals(employee.getAd())
						&& soyad.equals(employee.getSoyad())
						&& telNo.equals(employee.getTelNo()))
					session.delete("MUSTERI", employee);
			}
			session.flush();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
		} finally {
			session.close();
		}

	}

	private void selectedTable(Long id) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Musteri employee = (Musteri) session.get(Musteri.class, id);
			txtMusteriNoRecord.setValue(employee.getMusteriNo());
			txtTcNoRecord.setValue(employee.getTcNo());
			txtAdRecord.setValue(employee.getAd());
			txtSoyadRecord.setValue(employee.getSoyad());
			cmbIlRecord.setValue("");
			cmbIlceRecord.setValue("");
			cmbMahalleRecord.setValue("");
			txtTelNoRecord.setValue(employee.getTelNo());
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}