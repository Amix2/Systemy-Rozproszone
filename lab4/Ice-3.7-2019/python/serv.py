import sys, Ice
import Demo
 
class CalcI(Demo.Calc):
    def add(self, a, b, current=None):
        print("add")
        if(current):
            print(current.ctx)
        return a+b;
    def subtract(self, a, b, current=None):
        print("sub")
        return a-b;

 
with Ice.initialize(sys.argv) as communicator:
    adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h localhost -p 10000:udp -h localhost -p 10000")

    calc = CalcI()
    adapter.add(calc, communicator.stringToIdentity("calc/calc11"))
    adapter.activate()
    print("waiting")
    communicator.waitForShutdown()