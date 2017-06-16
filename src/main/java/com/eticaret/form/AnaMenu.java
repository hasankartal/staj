package com.eticaret.form;

import com.eticaret.form.musteri.MusteriCRUD;
import com.eticaret.form.siparis.SiparisCRUD;
import com.eticaret.form.urun.UrunCRUD;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class AnaMenu extends VerticalLayout {
	private Tree treeMenu;
	private VerticalLayout mainLayout;
	private HorizontalSplitPanel splitPanel;

	public AnaMenu() {
		splitPanel = new HorizontalSplitPanel();
		setSizeFull();
		buildTree();
		buildMainLayout();
		splitPanel.setFirstComponent(treeMenu);
		splitPanel.setSecondComponent(mainLayout);
		splitPanel.setSplitPosition(15, Unit.PERCENTAGE);
		splitPanel.setLocked(true);
		addComponent(splitPanel);
	}

	private void buildMainLayout() {
		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
	}

	private void buildTree() {
		treeMenu = new Tree();
		treeMenu.addItem("Müşteri");
		treeMenu.addItem("Ürün");
		treeMenu.addItem("Sipariş");

		treeMenu.setChildrenAllowed("Müşteri", false);
		treeMenu.setChildrenAllowed("Ürün", false);
		treeMenu.setChildrenAllowed("Sipariş", false);

		treeMenu.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.getItemId().toString().equals("Müşteri")) {
					mainLayout.removeAllComponents();
					mainLayout.addComponent(new MusteriCRUD());
				} else if (event.getItemId().toString().equals("Ürün")) {
					mainLayout.removeAllComponents();
					mainLayout.addComponent(new UrunCRUD());
				} else if (event.getItemId().toString().equals("Sipariş")) {
					mainLayout.removeAllComponents();
					mainLayout.addComponent(new SiparisCRUD());
				}
			}
		});
	}
}