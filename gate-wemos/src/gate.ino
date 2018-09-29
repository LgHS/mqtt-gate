#include <SPI.h>
#include <RFID.h>
#include <Adafruit_NeoPixel.h>
#ifdef __AVR__
  #include <avr/power.h>
#endif

// Which pin on the Arduino is connected to the NeoPixels?
// On a Trinket or Gemma we suggest changing this to 1
#define PIN            D1

// How many NeoPixels are attached to the Arduino?
#define NUMPIXELS      1
Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);


const char VOLET = D2;

RFID monModuleRFID(D8,D3);

int UID[5]={};

#include "config.h"

void setup()
{
  Serial.begin(9600);
  SPI.begin();
  monModuleRFID.init();
  pinMode(VOLET, OUTPUT);

  digitalWrite(VOLET, LOW);
  #if defined (__AVR_ATtiny85__)
  if (F_CPU == 16000000) clock_prescale_set(clock_div_1);
#endif
  pixels.begin(); // This initializes the NeoPixel library.

    pixels.setPixelColor(0, pixels.Color(0,0,0)); // Moderately bright green color.
    pixels.show(); // This sends the updated pixel color to the hardware.
}

void loop()
{
    if (monModuleRFID.isCard()) {
          if (monModuleRFID.readCardSerial()) {
            Serial.print("L'UID est: ");
            for(int i=0;i<=4;i++)
            {
              UID[i]=monModuleRFID.serNum[i];
              Serial.print(UID[i],DEC);
              Serial.print(".");
            }
            Serial.println("");
          }

          if (UID[0] == MASTERKEY[0]
           && UID[1] == MASTERKEY[1]
           && UID[2] == MASTERKEY[2]
           && UID[3] == MASTERKEY[3]
           && UID[4] == MASTERKEY[4])
          {
            Serial.println("ACCES OK");
              digitalWrite(VOLET, HIGH);
              pixels.setPixelColor(0, pixels.Color(0,150,0)); // Moderately bright green color.
              pixels.show(); // This sends the updated pixel color to the hardware.
              delay(800);
              digitalWrite(VOLET, LOW);
              delay(1000);
              pixels.setPixelColor(0, pixels.Color(0,0,0)); // Moderately bright green color.
              pixels.show(); // This sends the updated pixel color to the hardware.
          }
          else
          {
            Serial.println("ACCES NOK");
              pixels.setPixelColor(0, pixels.Color(150,0,0)); // Moderately bright green color.
              pixels.show(); // This sends the updated pixel color to the hardware.
              delay(1500);
              pixels.setPixelColor(0, pixels.Color(0,0,0)); // Moderately bright green color.
              pixels.show(); // This sends the updated pixel color to the hardware.
          }
          monModuleRFID.halt();
    }
    delay(1);
}
