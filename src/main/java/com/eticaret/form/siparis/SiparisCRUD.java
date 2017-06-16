package com.eticaret.form.siparis;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.eticaret.form.musteri.MySubMusteri;
import com.eticaret.form.urun.MySubUrun;
import com.eticaret.staj.HibernateUtil;
import com.eticaret.staj.Musteri;
import com.eticaret.staj.Siparis;
import com.eticaret.staj.Urun;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.BaseTheme;

public class SiparisCRUD extends VerticalSplitPanel {
	private VerticalSplitPanel verticalPanel;
	private AbsoluteLayout absLayoutSearch;
	private Label lblEkranSearch;
	private TextField txtAdetSearch;
	private DateField dateSiparisTarihSearch;
	private Button popUpMusteriSecSearch;
	private Button popUpMusteriSilSearch;
	private TextField txtPopUpMusteriSearch;
	private Button popUpUrunSecSearch;
	private Button popUpUrunSilSearch;
	private TextField txtPopUpUrunSearch;
	private Button btnYeniSearch;
	private Button btnAraSearch;
	private MySubMusteri subWindowMusteriSearch;
	private MySubUrun subWindowUrunSearch;
	private Musteri selectedMusteriSearch = null;
	private Urun selectedUrunSearch = null;

	private AbsoluteLayout absLayoutRecord;
	private Label lblEkranRecord;
	private TextField txtAdetRecord;
	private DateField dateSiparisTarihRecord;
	private Button popUpMusteriSecRecord;
	private Button popUpMusteriSilRecord;
	private TextField txtPopUpMusteriRecord;
	private Button popUpUrunSecRecord;
	private Button popUpUrunSilRecord;
	private TextField txtPopUpUrunRecord;
	private Button btnYeniRecord;
	private Button btnKaydetRecord;
	private Button btnSilRecord;
	private Table table;

	private MySubMusteri subWindowMusteri;
	private MySubUrun subWindowUrun;
	private Musteri selectedMusteri = null;
	private Urun selectedUrun = null;

	public SiparisCRUD() {
		setFirstComponent(buildForm());
		setSecondComponent(buildRecord());
		setSplitPosition(83, Unit.PERCENTAGE);
		setLocked(true);
		setHeight("120%");
	}

	private VerticalSplitPanel buildForm() {
		verticalPanel = new VerticalSplitPanel();
		verticalPanel.setFirstComponent(buildSearch());
		verticalPanel.setSecondComponent(buildTable());
		verticalPanel.setSplitPosition(22, Unit.PERCENTAGE);
		verticalPanel.setLocked(true);
		return verticalPanel;
	}

	private AbsoluteLayout buildSearch() {
		absLayoutSearch = new AbsoluteLayout();

		lblEkranSearch = new Label();
		lblEkranSearch.setCaption("Sipariş Arama Ekranı");
		absLayoutSearch.addComponent(lblEkranSearch, "left: 20px; top: 20px;");

		txtPopUpMusteriSearch = new TextField();
		txtPopUpMusteriSearch.setCaption("Musteri");
		absLayoutSearch.addComponent(txtPopUpMusteriSearch,
				"left: 20px; top: 50px;");

		popUpMusteriSecSearch = new Button();
		popUpMusteriSecSearch.setCaption("Sec");
		popUpMusteriSecSearch.setWidth("40");
		popUpMusteriSecSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				subWindowMusteriSearch = new MySubMusteri();
				UI.getCurrent().addWindow(subWindowMusteriSearch);

				subWindowMusteriSearch.addCloseListener(new CloseListener() {
					@Override
					public void windowClose(CloseEvent e) {
						selectedMusteriSearch = subWindowMusteriSearch
								.getSelectedMusteri();
						txtPopUpMusteriSearch.setValue("("
								+ selectedMusteriSearch.getId().toString()
								+ ") " + selectedMusteriSearch.getAd());
					}
				});
			}
		});
		absLayoutSearch.addComponent(popUpMusteriSecSearch,
				"left: 160px; top: 50px;");

		popUpMusteriSilSearch = new Button();
		popUpMusteriSilSearch.setCaption("Sil");
		popUpMusteriSilSearch.setWidth("40");
		popUpMusteriSilSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				txtPopUpMusteriSearch.setValue("");
				selectedMusteriSearch = null;
			}
		});
		absLayoutSearch.addComponent(popUpMusteriSilSearch,
				"left: 205px; top: 50px;");

		txtPopUpUrunSearch = new TextField();
		txtPopUpUrunSearch.setCaption("Urun");
		absLayoutSearch.addComponent(txtPopUpUrunSearch,
				"left: 320px; top: 50px;");

		popUpUrunSecSearch = new Button();
		popUpUrunSecSearch.setCaption("Sec");
		popUpUrunSecSearch.setWidth("40");
		popUpUrunSecSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				subWindowUrunSearch = new MySubUrun();
				UI.getCurrent().addWindow(subWindowUrunSearch);

				subWindowUrunSearch.addCloseListener(new CloseListener() {
					@Override
					public void windowClose(CloseEvent e) {
						selectedUrunSearch = subWindowUrunSearch
								.getSelectedUrun();
						txtPopUpUrunSearch.setValue("("
								+ selectedUrunSearch.getId().toString() + ") "
								+ selectedUrunSearch.getMarka());
					}
				});
			}
		});
		absLayoutSearch.addComponent(popUpUrunSecSearch,
				"left: 460px; top: 50px;");

		popUpUrunSilSearch = new Button();
		popUpUrunSilSearch.setCaption("Sil");
		popUpUrunSilSearch.setWidth("40");
		popUpUrunSilSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				txtPopUpUrunSearch.setValue("");
				selectedUrunSearch = null;
			}
		});
		absLayoutSearch.addComponent(popUpUrunSilSearch,
				"left: 505px; top: 50px;");

		txtAdetSearch = new TextField();
		txtAdetSearch.setCaption("Adet");
		absLayoutSearch.addComponent(txtAdetSearch, "left: 620px; top: 50px;");

		dateSiparisTarihSearch = new DateField();
		dateSiparisTarihSearch.setCaption("Sipariş Tarihi");
		absLayoutSearch.addComponent(dateSiparisTarihSearch,
				"left: 920px; top: 50px;");

		btnYeniSearch = new Button();
		btnYeniSearch.setCaption("Yeni");
		btnYeniSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				txtPopUpMusteriSearch.setValue("");
				selectedMusteriSearch = null;
				txtPopUpUrunSearch.setValue("");
				selectedUrunSearch = null;
				txtAdetSearch.setValue("");
				dateSiparisTarihSearch.setValue(null);
			}
		});
		absLayoutSearch.addComponent(btnYeniSearch, "left: 20px; top: 85px;");

		btnAraSearch = new Button();
		btnAraSearch.setCaption("Ara");
		btnAraSearch.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				searchInDatabase(selectedMusteriSearch, selectedUrunSearch,
						txtAdetSearch.getValue(),
						dateSiparisTarihSearch.getValue(),
						txtPopUpMusteriSearch.getValue(),
						txtPopUpUrunSearch.getValue());
				Notification.show("Arama işlemi gerçekleştirildi.");
			}
		});
		absLayoutSearch.addComponent(btnAraSearch, "left: 80px; top: 85px;");

		return absLayoutSearch;
	}

	private void searchInDatabase(Musteri musteri, Urun urun, String adet,
			Date siparisTarih, String string, String string2) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;
		table.removeAllItems();
		try {
			tx = session.beginTransaction();
			List orders = session.createQuery("FROM Siparis").list();
			if (musteri == null && urun == null) {
				for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
					Siparis order = (Siparis) iterator.next();
					if ( (adet.equals(order.getSiparisAdet()) || adet.equals(""))
					) {
					table.addItem(new Object[] {
							order.getMusteri().getId(),
							order.getUrun().getId(),
							order.getSiparisAdet(), null
							}, order);
					}
					
					else if ((string.equals("")) && (string2.equals(""))
							&& (adet.equals(""))
					) {
						table.addItem(
								new Object[] { order.getMusteri().getId()+" "+ order.getMusteri().getAd(),
										order.getUrun(),
										order.getSiparisAdet(), null
								}, order);
					}

				}

			} else if (musteri == null) {
				for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
					Siparis order = (Siparis) iterator.next();
					if ( (urun.getId().equals(order.getUrun().getId()) || string2
									.equals(""))
							&& (adet.equals(order.getSiparisAdet()) || adet
									.equals(""))
					) {

						table.addItem(new Object[] {
								order.getMusteri().getId(),
								order.getUrun().getId(),
								order.getSiparisAdet(),null 
								}, order);
					}

					else if ((string.equals("")) && (string2.equals(""))
							&& (adet.equals(""))
					) {
						table.addItem(
								new Object[] { order.getMusteri(),
										order.getUrun(),
										order.getSiparisAdet(), null
										}, order);
					}

				}
			} else if (urun == null) {
				for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
					Siparis order = (Siparis) iterator.next();
					if ((musteri.getId().equals(order.getMusteri().getId()) || string
							.equals(""))
							&& (adet.equals(order.getSiparisAdet()) || adet
									.equals(""))
					) {

						table.addItem(new Object[] {
								order.getMusteri().getId(),
								order.getUrun().getId(),
								order.getSiparisAdet(),null 
								}, order);
					}
					else if ((string.equals("")) && (string2.equals(""))
							&& (adet.equals(""))			
					) {
						table.addItem(
								new Object[] { order.getMusteri(),
										order.getUrun(),
										order.getSiparisAdet(), null
										}, order);
					}

				}
			} else {
				for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
					Siparis order = (Siparis) iterator.next();
					if ((musteri.getId().equals(order.getMusteri().getId()) || string
							.equals(""))
							&& (urun.getId().equals(order.getUrun().getId()) || string2
									.equals(""))
							&& (adet.equals(order.getSiparisAdet()) || adet
									.equals(""))
					) {

						table.addItem(new Object[] {
								order.getMusteri().getId(),
								order.getUrun().getId(),
								order.getSiparisAdet(), null
								}, order.getId());
					}

					else if ((string.equals("")) && (string2.equals(""))
							&& (adet.equals(""))
					) {
						table.addItem(
								new Object[] { order.getMusteri(),
										order.getUrun(),
										order.getSiparisAdet(), null
								}, order.getId());
					}

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

		table.addContainerProperty("Müşteri ", Long.class, null);
		table.addContainerProperty("Ürün ", Long.class, null);
		table.addContainerProperty("Adet", String.class, null);
		table.addContainerProperty("Tarih", String.class, null);
		table.setSizeFull();
		table.setImmediate(true);
		table.setSelectable(true);
		
		table.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					selectedTable((Long) event.getProperty().getValue());
				}
			}
		});
		return table;
	}

	private AbsoluteLayout buildRecord() {
		absLayoutRecord = new AbsoluteLayout();

		lblEkranRecord = new Label();
		lblEkranRecord.setCaption("Sipariş Kayıt Ekranı");
		absLayoutRecord.addComponent(lblEkranRecord, "left: 20px; top: 20px;");

		txtPopUpMusteriRecord = new TextField();
		txtPopUpMusteriRecord.setCaption("Musteri");
		absLayoutRecord.addComponent(txtPopUpMusteriRecord,
				"left: 20px; top: 50px;");

		popUpMusteriSecRecord = new Button();
		popUpMusteriSecRecord.setCaption("Sec");
		popUpMusteriSecRecord.setWidth("40");
		popUpMusteriSecRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				subWindowMusteri = new MySubMusteri();
				UI.getCurrent().addWindow(subWindowMusteri);

				subWindowMusteri.addCloseListener(new CloseListener() {
					@Override
					public void windowClose(CloseEvent e) {
						selectedMusteri = subWindowMusteri.getSelectedMusteri();
						txtPopUpMusteriRecord.setValue("("
								+ selectedMusteri.getId().toString() + ") "
								+ selectedMusteri.getAd());
					}
				});
			}
		});
		absLayoutRecord.addComponent(popUpMusteriSecRecord,
				"left: 160px; top: 50px;");

		popUpMusteriSilRecord = new Button();
		popUpMusteriSilRecord.setCaption("Sil");
		popUpMusteriSilRecord.setWidth("40");
		popUpMusteriSilRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				txtPopUpMusteriRecord.setValue("");
				selectedMusteri = null;
			}
		});
		absLayoutRecord.addComponent(popUpMusteriSilRecord,
				"left: 205px; top: 50px;");

		txtPopUpUrunRecord = new TextField();
		txtPopUpUrunRecord.setCaption("Urun");
		absLayoutRecord.addComponent(txtPopUpUrunRecord,
				"left: 320px; top: 50px;");

		popUpUrunSecRecord = new Button();
		popUpUrunSecRecord.setCaption("Sec");
		popUpUrunSecRecord.setWidth("40");
		popUpUrunSecRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				subWindowUrun = new MySubUrun();
				UI.getCurrent().addWindow(subWindowUrun);

				subWindowUrun.addCloseListener(new CloseListener() {
					@Override
					public void windowClose(CloseEvent e) {
						selectedUrun = subWindowUrun.getSelectedUrun();
						txtPopUpUrunRecord.setValue("("
								+ selectedUrun.getId().toString() + ") "
								+ selectedUrun.getMarka());
					}
				});
			}
		});
		absLayoutRecord.addComponent(popUpUrunSecRecord,
				"left: 460px; top: 50px;");

		popUpUrunSilRecord = new Button();
		popUpUrunSilRecord.setCaption("Sil");
		popUpUrunSilRecord.setWidth("40");
		popUpUrunSilRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				txtPopUpUrunRecord.setValue("");
				selectedUrun = null;
			}
		});
		absLayoutRecord.addComponent(popUpUrunSilRecord,
				"left: 505px; top: 50px;");

		txtAdetRecord = new TextField();
		txtAdetRecord.setCaption("Adet");
		absLayoutRecord.addComponent(txtAdetRecord, "left: 620px; top: 50px;");

		dateSiparisTarihRecord = new DateField();
		dateSiparisTarihRecord.setCaption("Sipariş Tarihi");
		absLayoutRecord.addComponent(dateSiparisTarihRecord,
				"left: 920px; top: 50px;");

		btnYeniRecord = new Button();
		btnYeniRecord.setCaption("Yeni");
		btnYeniRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				txtPopUpMusteriRecord.setValue("");
				selectedMusteri = null;
				txtPopUpUrunRecord.setValue("");
				selectedUrun = null;
				txtAdetRecord.setValue("");
				dateSiparisTarihRecord.setValue(null);
			}
		});
		absLayoutRecord.addComponent(btnYeniRecord, "left: 20px; top: 85px;");

		btnKaydetRecord = new Button();
		btnKaydetRecord.setCaption("Kaydet");
		btnKaydetRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				addOrder(selectedMusteri, selectedUrun,
						txtAdetRecord.getValue(),
						(Date) dateSiparisTarihRecord.getValue());
				if(selectedMusteri != null && selectedUrun != null)
					Notification.show("Kaydetme işlemi gerçekleştirildi");
				
			}
			
		});
		absLayoutRecord.addComponent(btnKaydetRecord, "left: 80px; top: 85px;");

		btnSilRecord = new Button();
		btnSilRecord.setCaption("Sil");
		btnSilRecord.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (selectedMusteri == null) {
					Notification.show("Müsteriyi seciniz");
				} else if (selectedUrun == null) {
					Notification.show("Ürünü seciniz");
				} else {
					deleteOrder(selectedMusteri, selectedUrun,
							txtAdetRecord.getValue(),
							(Date) dateSiparisTarihRecord.getValue());
				}
			}

		});
		absLayoutRecord.addComponent(btnSilRecord, "left: 150px; top: 85px;");

		return absLayoutRecord;
	}

	private void addOrder(Musteri selectedMusterii, Urun selectedUrunn,
			String adet, Date siparisTarih) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;
		if (selectedMusteri == null) {
			Notification.show("Müsteriyi seciniz");
		} else if (selectedUrun == null) {
			Notification.show("Ürünü seciniz");
		} else {
			try {
				tx = session.beginTransaction();
				Siparis siparis = new Siparis();
				siparis.setMusteri(selectedMusterii);
				siparis.setUrun(selectedUrunn);
				siparis.setSiparisAdet(adet);
				siparis.setSiparisTarih(siparisTarih);

				session.save(siparis);
				session.flush();
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
			} finally {
				session.close();
			}
		}
	}

	private void deleteOrder(Musteri selectedMusterii, Urun selectedUrunn,
			String adet, Date siparisTarih) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Siparis siparis = new Siparis();
			siparis.setMusteri(selectedMusterii);
			siparis.setUrun(selectedUrunn);
			siparis.setSiparisAdet(adet);
			siparis.setSiparisTarih(siparisTarih);
			List orders = session.createQuery("FROM Siparis").list();
			for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
				Siparis order = (Siparis) iterator.next();
				if (selectedMusterii.getId().equals(order.getMusteri().getId())
						&& selectedUrunn.getId()
								.equals(order.getUrun().getId())
						&& adet.equals(order.getSiparisAdet())
				// && siparisTarih.equals(order.getSiparisTarih())
				)
					session.delete("SIPARIS", order);
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
			Siparis order = (Siparis) session.get(Siparis.class, id);
			txtPopUpMusteriRecord.setValue("("
					+ selectedMusteri.getId().toString() + ") "
					+ selectedMusteri.getAd().toString());
			txtPopUpUrunRecord.setValue("(" + selectedUrun.getId().toString()
					+ ") " + selectedUrun.getMarka());
			txtAdetRecord.setValue(order.getSiparisAdet());
			dateSiparisTarihRecord.setValue(order.getSiparisTarih());
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
		} finally {
			session.close();
		}
	}
}