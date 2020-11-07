# Web_Applications_Users_App_JSF_CDI_Beans
Simple web application plataform, for user's to login and communicate through messages with eachother. Developed using Java Server Faces (JSF) and CDI Beans.

## Development:


### Juan Javier
* Notificación de mensajes nuevos y no leídos
* Register, Login, Logout, ServerBean, DatabaseBean:

### David
* Desarrollo de websocket
* Online users list
* Lectura y borrado de mensajes
* Página de bandeja de entrada
* Tomar los mensajes que corresponden al usuario
* Actualización de lista del servidor por websocket push
* Diferenciar entre mensajes leídos y no leídos 

### Manolo
* Send Message, Read Message, Delete Message
* Lectura y borrado de mensajes
* Página de bandeja de entrada
* Tomar los mensajes que corresponden al usuario
* Funcionalidades de la lista, online/offline
* Actualización de lista del servidor por websocket push
* Diferenciar entre mensajes leídos y no leídos 
* Envió de mensajes que utilice la clase y sean multicast

Existe una alerta al momento de hacer logout que indica que el viewscope del home ya no es válido porque la sesión del browser ha sido cerrada, posiblemente antes de redireccionar a la página de login. No interrumpe funcionalidades.