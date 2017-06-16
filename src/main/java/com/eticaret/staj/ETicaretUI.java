package com.eticaret.staj;

import javax.servlet.annotation.WebServlet;
import com.eticaret.form.AnaMenu;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;


@Title("E-Ticaret")
@Theme("mytheme")
@SuppressWarnings("serial")
public class ETicaretUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ETicaretUI.class, widgetset = "com.eticaret.staj.AppWidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		setContent(new AnaMenu());
	}
}