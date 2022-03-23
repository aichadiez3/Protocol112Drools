module Protocol112Drools
{
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires org.kie.api;
	requires jdk.compiler;
	requires java.base;
	requires jfoenix;
	
	opens application to javafx.graphics, javafx.fxml;
}
