# BTVM - Bluetooth Vehicle Monitoring
Bluetooth Vehicle Monitoring is an Android Application that connects with an OBD2 adapter via Bluetooth. This app records
metrics from the vehicle for each trip taken with it running. An OBD2 adapter is required for this app to work.

## Download
BTVM is available on the play store here: (Place Holder)(https://github.com/caseychr/bluetooth-service-library)

## About BTVM
The motivation for this application arose from my professional work. At my company we needed a way for our apps to read
information off our clients' vehicles and report those metrics back to the server. The clients would then use those metrics in
their own reporting. Therefore I created a Bluetooth Library that connects with OBD2 vehicle adapters and reads those metrics
from the adapter. I created BTVM in order to better understand how the Bluetooth Library needed to be used by a developer
implementing it as well as use it in servicing my own vehicle.
The Bluetooth Service Library this app uses can be found here: (https://github.com/caseychr/bluetooth-service-library)

## Features
* Checks that user is indeed connected with device
* Polls for vehicle speed, air flow, odometer (), RPMs and coolant temperature for each trip initiated by the user
* Tracks the vehicle's location during each trip
* Displays each trip's average metrics and the vehicle's overall metrics for the life of the application

<img src="https://user-images.githubusercontent.com/11616798/65959750-d5e2ae80-e420-11e9-934f-b113a7cc05bb.png" width="100"/> <img src="https://user-images.githubusercontent.com/11616798/65959750-d5e2ae80-e420-11e9-934f-b113a7cc05bb.png" width="100"/> <img src="https://user-images.githubusercontent.com/11616798/65959750-d5e2ae80-e420-11e9-934f-b113a7cc05bb.png" width="100"/>


## Archectures & Libraries
* MVVM with LiveData
* Dagger 2
* Bluetooth Service Library
