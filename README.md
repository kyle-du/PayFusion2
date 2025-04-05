# PayFusion
A utility package for payment services. Supports a standard set of interfaces to abstract PayPal, Stripe, and Square order creation and capturing.

Usage
- Add a file ```PayFusionConstants.json``` to classpath. Example of formatting on GitHub under ```src/main/resources```.
- Run ```Constants.init()```.
- Define a ```MasterProductDefinition``` object using one of the constructors.
    - Options - default, default imageless, existing Stripe ID, and existing Stripe ID imageless.
- Create any ```Order``` object using either the ```PayPalOrder```, ```StripeOrder```, or ```SquareOrder``` constructor.
    - All have ```MasterProductionDefinition```, ```quantity``` as parameters.
- Run ```getOrderURL()``` to get checkout URL. 

Maven Link: [here](https://central.sonatype.com/artifact/io.github.kyle-du/payfusion2/overview)
