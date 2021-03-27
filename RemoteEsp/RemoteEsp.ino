#include <ESP8266WiFi.h>
//Motor A
int m11 = 5;
int m12 = 16;
//Motor B
int m21 = 12;  
int m22 = 13;

int h = 4;
int l = 2;

WiFiClient client;
WiFiServer server(80);

const char* ssid = "Burj Khalifa";
const char* password = "Sumit321";

String  data ="";

void setup() 
{ 
  pinMode(m11, OUTPUT); 
  pinMode(m12, OUTPUT);
  pinMode(m21, OUTPUT); 
  pinMode(m22, OUTPUT);
  pinMode(l, OUTPUT); 
  pinMode(h, OUTPUT);
    
  Serial.begin(115200);
  connectWiFi();
  server.begin();
}

void loop() 
{
    client = server.available();
    if (!client) return; 
    data = checkClient();
    Serial.println(data);

    if (data == "forward")
      mvForward();
    else if (data == "backward") 
      mvBackward();
    else if (data == "left")
      mvLeft();
    else if (data == "right")
      mvRight();
    else if (data == "horn")
      mvHorn();
    else if (data == "light")
      mvLight();
    else if (data == "stop")
      stopM();

    Serial.println("New loop");
}

void stopM(){
  digitalWrite(m11, LOW);
  digitalWrite(m12, LOW);
  digitalWrite(m21, LOW);
  digitalWrite(m22, LOW);
}

void mvForward(){
  digitalWrite(m11, HIGH);
  digitalWrite(m12, LOW);
  digitalWrite(m21, LOW);
  digitalWrite(m22, LOW);
}

void mvBackward(){
  digitalWrite(m11, LOW);
  digitalWrite(m12, HIGH);
  digitalWrite(m21, LOW);
  digitalWrite(m22, LOW);
}

void mvLeft(){
  digitalWrite(m11, HIGH);
  digitalWrite(m12, LOW);
  digitalWrite(m21, HIGH);
  digitalWrite(m22, LOW);
}

void mvRight(){
  digitalWrite(m11, HIGH);
  digitalWrite(m12, LOW);
  digitalWrite(m21, LOW);
  digitalWrite(m22, HIGH);
}

void mvHorn(){
  
}

void mvLight(){
  
}

String checkClient (void)
{
  while(!client.available())
    delay(1);
  String request = client.readStringUntil('\r');

  Serial.println(request);
  
  request.remove(0, 5);
  request.remove(request.length()-9,9);
  return request;
}

void connectWiFi()
{
  Serial.println("Connecting to WIFI");
  WiFi.begin(ssid, password);
  while ((!(WiFi.status() == WL_CONNECTED)))
  {
    delay(300);
    Serial.print("..");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("NodeMCU Local IP is : ");
  Serial.print((WiFi.localIP()));
}
