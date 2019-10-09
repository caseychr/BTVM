# BTVM - Bluetooth Vehicle Monitoring
Bluetooth Vehicle Monitoring is an Android Application that connects with an OBD2 adapter via Bluetooth. This app records
metrics from the vehicle for each trip taken with it running. An OBD2 adapter is required for this app to work.

<img src="https://user-images.githubusercontent.com/11616798/65960747-2529de80-e423-11e9-9298-cdb5ae2c7d4d.png" width="200"/>

## Download
BTVM is available on the play store here: https://play.google.com/store/apps/details?id=com.bluetoothvehiclemonitor.btvm

## About BTVM
The motivation for this application arose from my professional work. At my company we needed a way for our apps to read
information off our clients' vehicles and report those metrics back to the server. The clients would then use those metrics in
their own reporting. Therefore I created a Bluetooth Library that connects with OBD2 vehicle adapters and reads those metrics
from the adapter. I created BTVM in order to better understand how the Bluetooth Library needed to be used by a developer
implementing it as well as use it in servicing my own vehicle.
The Bluetooth Service Library this app uses can be found here: https://github.com/caseychr/bluetooth-service-library

## Features
* Checks that user is indeed connected with device
* Polls for vehicle speed, air flow, distance, RPMs and coolant temperature for each trip initiated by the user
* Tracks the vehicle's location during each trip
* Displays each trip's average metrics and the vehicle's overall metrics for the life of the application


<img src="https://user-images.githubusercontent.com/11616798/65959750-d5e2ae80-e420-11e9-934f-b113a7cc05bb.png" width="200"/> <img src="https://user-images.githubusercontent.com/11616798/65960569-c95f5580-e422-11e9-8550-b02ce7a46b27.png" width="200"/> <img src="https://user-images.githubusercontent.com/11616798/65960652-f0b62280-e422-11e9-9eab-ae49c6987061.png" width="200"/>


## Archectures & Libraries
* MVVM with LiveData
* Dagger 2
* Bluetooth Service Library
