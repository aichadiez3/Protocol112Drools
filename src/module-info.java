module Protocol112
{
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires jfoenix;
	requires java.sql;
	requires org.kie.api;
	
	opens application to javafx.graphics, javafx.fxml;
}
