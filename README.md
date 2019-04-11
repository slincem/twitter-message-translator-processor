# twitter-message-translator-processor
Proyecto Java Standalone. Se hace uso de RxJava, Apache Kafka y MongoDB en una primer instancia. Probando su uso agnostico a un framework. Esta aplicacion es un Consumidor. El productor es un Flow en Apache Nifi que extrae Mensajes de Twitter, y luego obtiene solo el contenido y el lenguaje del mismo.

Esta aplicación es solo un consumidor.

El productor, en este caso se trabajo con Apache Nifi. Los templates se encuentran en el proyecto; en especifico aquel que menciona a Kafka.

Una vez importado este template en Nifi, es necesario configurar las credenciales de Twitter, para poder hacer uso de su Api y 
asi recibir los tweets generados. Este procesador es llamado "GetTwitter", en el apartado de "Properties" son necesarios:

- Consumer Key
- Consumer Secret
- Access Token
- Access Token Secret

Todos estos son proveídos por Twitter.
