package com.eticaret.form.musteri;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.eticaret.staj.HibernateUtil;
import com.eticaret.staj.Musteri;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

public class MySubMusteri extends Window {
	private VerticalSplitPanel verticalPanel;
	private AbsoluteLayout absLayoutSearch;
	private TextField txtMusteriNoSearch;
	private TextField txtTcNoSearch;
	private TextField txtAdSearch;
	private TextField txtSoyadSearch;
	private ComboBox cmbIlSearch;
	private ComboBox cmbIlceSearch;
	private ComboBox cmbMahalleSearch;
	private TextField txtTelNoSearch;
	private Button btnAraSearch;
	private Button btnYeniSearch;
	private Table table;
	private Musteri selectedMusteri;

	public MySubMusteri() {
		super("Müşteri Arama Ekranı"); // Set window caption
		super.setHeight("560");
		super.setWidth("1075");
		center();
		super.setContent(buildForm());
		setClosable(true);
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

		txtMusteriNoSearch = new TextField();
		txtMusteriNoSearch.setCaption("Müşteri Numarası");
		absLayoutSearch.addComponent(txtMusteriNoSearch,
				"left: 20px; top: 35px;");

		txtTcNoSearch = new TextField();
		txtTcNoSearch.setCaption("T.C. Kimlik Numarası");
		absLayoutSearch.addComponent(txtTcNoSearch, "left: 320px; top: 35px;");

		txtAdSearch = new TextField();
		txtAdSearch.setCaption("Ad");
		absLayoutSearch.addComponent(txtAdSearch, "left: 620px; top: 35px;");

		txtSoyadSearch = new TextField();
		txtSoyadSearch.setCaption("Soyad");
		absLayoutSearch.addComponent(txtSoyadSearch, "left: 920px; top: 35px;");

		cmbIlSearch = new ComboBox();
		cmbIlSearch.setCaption("İl");
		absLayoutSearch.addComponent(cmbIlSearch, "left: 20px; top: 85px;");

		cmbIlceSearch = new ComboBox();
		cmbIlceSearch.setCaption("İlçe");
		absLayoutSearch.addComponent(cmbIlceSearch, "left: 320px; top: 85px;");

		cmbMahalleSearch = new ComboBox();
		cmbMahalleSearch.setCaption("Mahalle");
		absLayoutSearch.addComponent(cmbMahalleSearch,
				"left: 620px; top: 85px;");

		txtTelNoSearch = new TextField();
		txtTelNoSearch.setCaption("Telefon Numarası");
		absLayoutSearch.addComponent(txtTelNoSearch, "left: 920px; top: 85px;");

		btnYeniSearch = new Button();
		btnYeniSearch.setCaption("Yeni");
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
		absLayoutSearch.addComponent(btnYeniSearch, "left: 20px; top: 120px;");

		btnAraSearch = new Button();
		btnAraSearch.setCaption("Ara");
		btnAraSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				searchInDatabase(txtMusteriNoSearch.getValue(),
						txtTcNoSearch.getValue(), txtAdSearch.getValue(),
						txtSoyadSearch.getValue(), txtTelNoSearch.getValue());
			}
		});
		absLayoutSearch.addComponent(btnAraSearch, "left: 80px; top: 120px;");

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
									employee.getTelNo() }, employee);
				}

				else if ((musteriNo.equals("")) && (TcNo.equals(""))
						&& (ad.equals("")) && (soyad.equals(""))
						&& (telNo.equals(""))) {
					table.addItem(
							new Object[] { employee.getMusteriNo(),
									employee.getTcNo(), employee.getAd(),
									employee.getSoyad(), null, null, null,
									employee.getTelNo() }, employee);
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

		table.setSizeFull();
		table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					selectedMusteri = (Musteri) event.getItemId();
					close();
				}
			}
		});

		return table;
	}

	public Musteri getSelectedMusteri() {
		return selectedMusteri;
	}

	public void setSelectedMusteri(Musteri selectedMusteri) {
		this.selectedMusteri = selectedMusteri;
	}
}