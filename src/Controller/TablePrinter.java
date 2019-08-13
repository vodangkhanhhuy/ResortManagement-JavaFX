/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author TechCare
 */
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * This class provides a method to print the content of a {@link TableView}. It
 * is at an early stage and currently doesn't support advanced CellFactorys.
 */
public class TablePrinter {
	public static <T> void print(TableView<T> tableView, PrinterJob jobArg) {
		boolean createJob = jobArg == null;
		PrinterJob job;
		if (createJob) {
			job = PrinterJob.createPrinterJob();
		} else {
			job = jobArg;
		}
		printWithJob(tableView, job);
		if (createJob) {
			job.endJob();
		}
	}
	private static <T> void printWithJob(TableView<T> tableView, PrinterJob job) {
		TableView<T> copyView = createTableCopy(tableView, job);
		ArrayList<T> itemList = new ArrayList<>(tableView.getItems());
		while (itemList.size() > 0) {
			printPage(job, copyView, itemList);
		}
	}

	private static <T> void printPage(PrinterJob job, TableView<T> copyView, ArrayList<T> itemList) {
		ScrollBar verticalScrollbar = getVerticalScrollbar(copyView);
		ObservableList<T> batchItemList = FXCollections.observableArrayList();
		copyView.setItems(batchItemList);
		batchItemList.add(itemList.remove(0));
		while (!verticalScrollbar.isVisible() && itemList.size() > 0) {
			T item = itemList.remove(0);
			batchItemList.add(item);
			copyView.layout();
		}
		if (batchItemList.size() > 1 && verticalScrollbar.isVisible()) {
			T item = batchItemList.remove(batchItemList.size() - 1);
			itemList.add(0, item);
			copyView.layout();
		}
		job.printPage(copyView);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> TableView<T> createTableCopy(TableView<T> tableView, PrinterJob job) {
		TableView<T> copyView = new TableView<>();
		PageLayout pageLayout = job.getJobSettings().getPageLayout();
		Paper paper = pageLayout.getPaper();
		double paperHeight = paper.getHeight() - pageLayout.getTopMargin() - pageLayout.getBottomMargin();
		double paperWidth = paper.getWidth() - pageLayout.getLeftMargin() - pageLayout.getRightMargin();
		if (pageLayout.getPageOrientation().equals(PageOrientation.PORTRAIT)) {
			copyView.setPrefHeight(paperHeight);
			copyView.setPrefWidth(paperWidth);
		} else {
			copyView.setPrefHeight(paperWidth);
			copyView.setPrefWidth(paperHeight);
		}
		copyView.setColumnResizePolicy(tableView.getColumnResizePolicy());
		for (TableColumn<T, ?> t : tableView.getColumns()) {
			TableColumn cloneColumn = new TableColumn(t.getText());
			cloneColumn.setMaxWidth(t.getMaxWidth());
			if (t.getCellValueFactory() != null) {
				cloneColumn.setCellValueFactory(t.getCellValueFactory());
			}
			if (t.getCellFactory() != null) {
				cloneColumn.setCellFactory(t.getCellFactory());
			}
			copyView.getColumns().add(cloneColumn);
		}
		new Scene(copyView);
		copyView.getScene().getStylesheets().add(TablePrinter.class.getResource("TablePrint.css").toString());
		return copyView;
	}

	private static <T> ScrollBar getVerticalScrollbar(TableView<T> tableView) {
		tableView.snapshot(new SnapshotParameters(), null);
		return getVerticalScrollbarForParent(tableView);
	}

	private static ScrollBar getVerticalScrollbarForParent(Parent p) {
		for (Node n : p.getChildrenUnmodifiable()) {
			if (n instanceof ScrollBar) {
				ScrollBar s = (ScrollBar) n;
				if (s.getOrientation() == Orientation.VERTICAL) {
					return s;
				}
			}
			if (n instanceof Parent && !(p instanceof TableCell)) {
				ScrollBar scrollbar = getVerticalScrollbarForParent((Parent) n);
				if (scrollbar != null) {
					return scrollbar;
				}
			}
		}
		return null;
	}
}