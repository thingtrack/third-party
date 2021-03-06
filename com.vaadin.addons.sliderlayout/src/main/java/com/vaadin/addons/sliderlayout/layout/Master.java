/*
 * Copyright 2012 Thingtrack S.L.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.addons.sliderlayout.layout;

import java.util.Date;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Item;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class Master extends CustomComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Table tableMaster;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public Master() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		tableMaster.setImmediate(true);
		tableMaster.setSelectable(true);
		tableMaster.setMultiSelect(false);
		tableMaster.setNullSelectionAllowed(false);
		
		tableMaster.addContainerProperty("Company", String.class, null);
		tableMaster.addContainerProperty("Client", String.class, null);
		tableMaster.addContainerProperty("Date",  Date.class, null);
				
		// Add rows to the table
		Object newItem01 = tableMaster.addItem();
		Item row01 = tableMaster.getItem(newItem01);
		row01.getItemProperty("Company").setValue("Thingtrack S.L.");
		row01.getItemProperty("Client").setValue("Miguel Angel");
		row01.getItemProperty("Date").setValue(new Date());

		Object newItem02 = tableMaster.addItem();
		Item row02 = tableMaster.getItem(newItem02);
		row02.getItemProperty("Company").setValue("Thingtrack S.L.");
		row02.getItemProperty("Client").setValue("Carlos Salinas");
		row02.getItemProperty("Date").setValue(new Date());

		Object newItem03 = tableMaster.addItem();
		Item row03 = tableMaster.getItem(newItem03);
		row03.getItemProperty("Company").setValue("Thingtrack S.L.");
		row03.getItemProperty("Client").setValue("Manrique Canteli");
		row03.getItemProperty("Date").setValue(new Date());
		
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// tableMaster
		tableMaster = new Table();
		tableMaster.setImmediate(false);
		tableMaster.setWidth("100.0%");
		tableMaster.setHeight("100.0%");
		mainLayout.addComponent(tableMaster);
		mainLayout.setExpandRatio(tableMaster, 1.0f);
		
		return mainLayout;
	}

}
