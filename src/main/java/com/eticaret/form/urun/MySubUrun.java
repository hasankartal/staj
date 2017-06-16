package com.eticaret.form.urun;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.eticaret.staj.HibernateUtil;
import com.eticaret.staj.Musteri;
import com.eticaret.staj.Urun;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

public class MySubUrun extends Window {
	private VerticalSplitPanel verticalPanel;
	private AbsoluteLayout absLayoutSearch;
	private TextField txtUrunNoSearch;
	private TextField txtUrunTurSearch;
	private TextField txtMarkaAdSearch;
	private TextField txtModelSearch;
	private TextField txtFiyatSearch;
	private Button btnYeniSearch;
	private Button btnAraSearch;
	private Table table;
	private Urun selectedUrun;

	public MySubUrun() {
		super("Urun Arama Ekranı"); // Set window caption
		super.setHeight("560");
		super.setWidth("1075");
		center();

		super.setContent(buildForm());
		// Disable the close button
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

		txtUrunNoSearch = new TextField();
		txtUrunNoSearch.setCaption("Urun Numarası");
		absLayoutSearch.addComponent(txtUrunNoSearch, "left: 20px; top: 35px;");

		txtUrunTurSearch = new TextField();
		txtUrunTurSearch.setCaption("Urun Adı");
		absLayoutSearch.addComponent(txtUrunTurSearch,
				"left: 320px; top: 35px;");

		txtMarkaAdSearch = new TextField();
		txtMarkaAdSearch.setCaption("Marka Adı");
		absLayoutSearch.addComponent(txtMarkaAdSearch,
				"left: 620px; top: 35px;");

		txtModelSearch = new TextField();
		txtModelSearch.setCaption("Model");
		absLayoutSearch.addComponent(txtModelSearch, "left: 920px; top: 35px;");

		txtFiyatSearch = new TextField();
		txtFiyatSearch.setCaption("Fiyat");
		absLayoutSearch.addComponent(txtFiyatSearch, "left: 20px; top: 85px;");

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
		absLayoutSearch.addComponent(btnYeniSearch, "left: 20px; top: 120px;");

		btnAraSearch = new Button();
		btnAraSearch.setCaption("Ara");
		btnAraSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				searchInDatabase(txtUrunNoSearch.getValue(),
						txtUrunTurSearch.getValue(),
						txtMarkaAdSearch.getValue(), txtModelSearch.getValue(),
						txtFiyatSearch.getValue());
			}
		});
		absLayoutSearch.addComponent(btnAraSearch, "left: 80px; top: 120px;");

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
							product);
				}

				else if ((urunNo.equals("")) && (urunTur.equals(""))
						&& (markaAd.equals("")) && (model.equals(""))
						&& (fiyat.equals(""))) {

					table.addItem(
							new Object[] { product.getUrunNo(),
									product.getTur(), product.getMarka(),
									product.getModel(), product.getFiyat() },
							product);
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

		table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					selectedUrun = (Urun) event.getItemId();
					close();
				}
			}
		});

		return table;
	}

	public Urun getSelectedUrun() {
		return selectedUrun;
	}

	public void setSelectedUrun(Urun selectedUrun) {
		this.selectedUrun = selectedUrun;
	}
}