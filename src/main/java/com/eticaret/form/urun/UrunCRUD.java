package com.eticaret.form.urun;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.eticaret.staj.HibernateUtil;
import com.eticaret.staj.Urun;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalSplitPanel;

public class UrunCRUD extends VerticalSplitPanel {
	private VerticalSplitPanel verticalPanel;
	private AbsoluteLayout absLayoutSearch;
	private Label lblEkranSearch;
	private TextField txtUrunNoSearch;
	private TextField txtUrunTurSearch;
	private TextField txtMarkaAdSearch;
	private TextField txtModelSearch;
	private TextField txtFiyatSearch;
	private Button btnYeniSearch;
	private Button btnAraSearch;

	private AbsoluteLayout absLayoutRecord;
	private Label lblEkranRecord;
	private TextField txtUrunNoRecord;
	private TextField txtUrunTurRecord;
	private TextField txtMarkaAdRecord;
	private TextField txtModelRecord;
	private TextField txtFiyatRecord;
	private Button btnYeniRecord;
	private Button btnKaydetRecord;
	private Button btnSilRecord;
	private Table table;

	public UrunCRUD() {
		setFirstComponent(buildForm());
		setSecondComponent(buildRecord());
		setSplitPosition(75, Unit.PERCENTAGE);
		setLocked(true);
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
		lblEkranSearch.setCaption("Ürün Arama Ekranı");
		absLayoutSearch.addComponent(lblEkranSearch, "left: 20px; top: 20px;");

		txtUrunNoSearch = new TextField();
		txtUrunNoSearch.setCaption("Urun Numarası");
		// txtUrunNoSearch.setConverter(Long.class);
		// txtUrunNoSearch.setNullRepresentation("");
		absLayoutSearch.addComponent(txtUrunNoSearch, "left: 20px; top: 50px;");

		txtUrunTurSearch = new TextField();
		txtUrunTurSearch.setCaption("Urun Adı");
		absLayoutSearch.addComponent(txtUrunTurSearch,
				"left: 320px; top: 50px;");

		txtMarkaAdSearch = new TextField();
		txtMarkaAdSearch.setCaption("Marka Adı");
		absLayoutSearch.addComponent(txtMarkaAdSearch,
				"left: 620px; top: 50px;");

		txtModelSearch = new TextField();
		txtModelSearch.setCaption("Model");
		absLayoutSearch.addComponent(txtModelSearch, "left: 920px; top: 50px;");

		txtFiyatSearch = new TextField();
		txtFiyatSearch.setCaption("Fiyat");
		// txtFiyatSearch.setConverter(Long.class);
		// txtFiyatSearch.setNullRepresentation("");
		absLayoutSearch.addComponent(txtFiyatSearch, "left: 20px; top: 100px;");

		btnYeniSearch = new Button();
		btnYeniSearch.setCaption("Yeni");
		btnYeniSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				txtUrunNoSearch.setValue("");
				txtUrunTurSearch.setValue("");
				txtMarkaAdSearch.setValue("");
				txtModelSearch.setValue("");
				txtFiyatSearch.setValue("");
			}
		});
		absLayoutSearch.addComponent(btnYeniSearch, "left: 20px; top: 135px;");

		btnAraSearch = new Button();
		btnAraSearch.setCaption("Ara");
		btnAraSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				searchInDatabase(txtUrunNoSearch.getValue(),
						txtUrunTurSearch.getValue(),
						txtMarkaAdSearch.getValue(), txtModelSearch.getValue(),
						txtFiyatSearch.getValue());
				Notification.show("Arama işlemi gerçekleştirildi.");
			}
			
		});
		absLayoutSearch.addComponent(btnAraSearch, "left: 80px; top: 135px;");

		return absLayoutSearch;
	}

	private void searchInDatabase(String urunNo, String urunTur,
			String markaAd, String model, String fiyat) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;
		table.removeAllItems();
		try {
			tx = session.beginTransaction();
			List products = session.createQuery("FROM Urun").list();

			for (Iterator iterator = products.iterator(); iterator.hasNext();) {
				Urun product = (Urun) iterator.next();
				if ((urunNo.equals(product.getUrunNo()) || urunNo.equals(""))
						&& (urunTur.equals(product.getTur()) || urunTur
								.equals(""))
						&& (markaAd.equals(product.getMarka()) || markaAd
								.equals(""))
						&& (model.equals(product.getModel()) || model
								.equals(""))
						&& (fiyat.equals(product.getFiyat()) || fiyat
								.equals(""))) {

					table.addItem(
							new Object[] { product.getUrunNo(),
									product.getTur(), product.getMarka(),
									product.getModel(), product.getFiyat() },
							product.getId());
				}

				else if ((urunNo.equals("")) && (urunTur.equals(""))
						&& (markaAd.equals("")) && (model.equals(""))
						&& (fiyat.equals(""))) {

					table.addItem(
							new Object[] { product.getUrunNo(),
									product.getTur(), product.getMarka(),
									product.getModel(), product.getFiyat() },
							product.getId());
				}

			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	private Table buildTable() {
		table = new Table();

		table.addContainerProperty("Ürün Numarası", String.class, null);
		table.addContainerProperty("Ürün Ad", String.class, null);
		table.addContainerProperty("Marka", String.class, null);
		table.addContainerProperty("Model", String.class, null);
		table.addContainerProperty("Fiyat", String.class, null);
		table.setSizeFull();
		table.setSelectable(true);
		
		table.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				if(event.getProperty().getValue() != null)
					selectedTable((Long) event.getProperty().getValue());
			}
		});
		
		return table;
	}

	private AbsoluteLayout buildRecord() {
		absLayoutRecord = new AbsoluteLayout();

		lblEkranRecord = new Label();
		lblEkranRecord.setCaption("Ürün Kayıt Ekranı");
		absLayoutRecord.addComponent(lblEkranRecord, "left: 20px; top: 20px;");

		txtUrunNoRecord = new TextField();
		txtUrunNoRecord.setCaption("Urun Numarası");
		// txtUrunNoRecord.setConverter(Long.class);
		// txtUrunNoRecord.setNullRepresentation("");
		absLayoutRecord.addComponent(txtUrunNoRecord, "left: 20px; top: 50px;");

		txtUrunTurRecord = new TextField();
		txtUrunTurRecord.setCaption("Urun Adı");
		absLayoutRecord.addComponent(txtUrunTurRecord,
				"left: 320px; top: 50px;");

		txtMarkaAdRecord = new TextField();
		txtMarkaAdRecord.setCaption("Marka Adı");
		absLayoutRecord.addComponent(txtMarkaAdRecord,
				"left: 620px; top: 50px;");

		txtModelRecord = new TextField();
		txtModelRecord.setCaption("Model");
		absLayoutRecord.addComponent(txtModelRecord, "left: 920px; top: 50px;");

		txtFiyatRecord = new TextField();
		txtFiyatRecord.setCaption("Fiyat");
		absLayoutRecord.addComponent(txtFiyatRecord, "left: 20px; top: 100px;");

		btnYeniRecord = new Button();
		btnYeniRecord.setCaption("Yeni");
		btnYeniRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				txtUrunNoRecord.setValue("");
				txtUrunTurRecord.setValue("");
				txtMarkaAdRecord.setValue("");
				txtModelRecord.setValue("");
				txtFiyatRecord.setValue("");
			}
		});
		absLayoutRecord.addComponent(btnYeniRecord, "left: 20px; top: 135px;");

		btnKaydetRecord = new Button();
		btnKaydetRecord.setCaption("Kaydet");
		btnKaydetRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				addProduct(txtUrunNoRecord.getValue(),
						txtUrunTurRecord.getValue(),
						txtMarkaAdRecord.getValue(), txtModelRecord.getValue(),
						txtFiyatRecord.getValue());
				Notification.show("Kaydetme işlemi gerçekleştirildi.");
			}
		});
		absLayoutRecord
				.addComponent(btnKaydetRecord, "left: 80px; top: 135px;");

		btnSilRecord = new Button();
		btnSilRecord.setCaption("Sil");
		btnSilRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				deleteProduct(txtUrunNoRecord.getValue(),
						txtUrunTurRecord.getValue(),
						txtMarkaAdRecord.getValue(), txtModelRecord.getValue(),
						txtFiyatRecord.getValue());
			}
		});
		absLayoutRecord.addComponent(btnSilRecord, "left: 150px; top: 135px;");

		return absLayoutRecord;
	}

	private void selectedTable(Long id) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Urun product = (Urun) session.get(Urun.class, id);
			txtUrunNoRecord.setValue(product.getUrunNo());
			txtUrunTurRecord.setValue(product.getTur());
			txtMarkaAdRecord.setValue(product.getMarka());
			txtModelRecord.setValue(product.getModel());
			txtFiyatRecord.setValue(product.getFiyat());
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private void addProduct(String urunNo, String urunTur, String markaAd,
			String model, String fiyat) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Urun urun = new Urun();
			urun.setUrunNo(urunNo);
			urun.setTur(urunTur);
			urun.setMarka(markaAd);
			urun.setModel(model);
			urun.setFiyat(fiyat);

			session.save(urun);
			session.flush();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private void deleteProduct(String urunNo, String urunTur, String markaAd,
			String model, String fiyat) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Urun urun = new Urun();
			urun.setUrunNo(urunNo);
			urun.setTur(urunTur);
			urun.setMarka(markaAd);
			urun.setModel(model);
			urun.setFiyat(fiyat);

			List products = session.createQuery("FROM Urun").list();
			for (Iterator iterator = products.iterator(); iterator.hasNext();) {
				Urun product = (Urun) iterator.next();
				if (urunNo.equals(product.getUrunNo())
						&& urunTur.equals(product.getTur())
						&& markaAd.equals(product.getMarka())
						&& model.equals(product.getModel())
						&& fiyat.equals(product.getFiyat()))
					session.delete("URUN", product);
			}
			session.flush();
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