#include <iostream>
#include <string>
using namespace std;

class Person {
public:
    string name, id, phoneNumber;
};

class ROOM {
protected:
    int RoomNumber;
    bool isOccupied = false;
    double price = 5000.0;
public:
    void setRoomNo(int n) { RoomNumber = n; }
    bool checkAvailability() { return !isOccupied; }
    void updateStatus(bool status) { isOccupied = status; }
    int getRoomNo() { return RoomNumber; }
    double getPrice() { return price; }
};

class ACROOM : public ROOM {
private:
    double acCharges = 1500.0;
    double maintenanceFee = 500.0;
public:
    double calculateBill() { return price + acCharges + maintenanceFee; }
};

class GUEST : public Person {
public:
    double balanceAmount;
    bool makePayment(double total) {
        if (balanceAmount >= total) {
            balanceAmount -= total;
            cout << "Payment Successful! New Balance: " << balanceAmount << endl;
            return true; 
        } else {
            cout << "Insufficient balance! Please add funds." << endl;
            return false;
        }
    }
};

class ADMIN : public Person {
public:
    void bookRoom(ROOM &r) { r.updateStatus(true); }
};

int main() {
    ACROOM hotelRooms[3];
    hotelRooms[0].setRoomNo(101);
    hotelRooms[1].setRoomNo(102);
    hotelRooms[2].setRoomNo(103);

    ADMIN admin;

    while (true) {
        GUEST g;
        int choiceIndex = -1;
        int roomInput;
        char acChoice;

        cout << "\n--- RestEasy Management System ---" << endl;
        cout << "Enter Guest Name (type 'exit' to quit): ";
        if(cin.peek() == '\n') cin.ignore(); 
        getline(cin, g.name);
        if (g.name == "exit") break;

        cout << "Enter ID: ";
        getline(cin, g.id);
        cout << "Enter Phone: ";
        getline(cin, g.phoneNumber);
        cout << "Enter Starting Balance: ";
        cin >> g.balanceAmount;

        cout << "\nAvailable Rooms: ";
        for(int i = 0; i < 3; i++) {
            if(hotelRooms[i].checkAvailability()) cout << hotelRooms[i].getRoomNo() << " ";
        }

        cout << "\nEnter Room Number to Book: ";
        cin >> roomInput;

        for(int i = 0; i < 3; i++) {
            if(hotelRooms[i].getRoomNo() == roomInput) {
                choiceIndex = i;
                break;
            }
        }

        if (choiceIndex != -1 && hotelRooms[choiceIndex].checkAvailability()) {
            cout << "Do you want an AC Room? (y/n): ";
            cin >> acChoice;

            double finalBill = (acChoice == 'y' || acChoice == 'Y') ? 
                               hotelRooms[choiceIndex].calculateBill() : 
                               hotelRooms[choiceIndex].getPrice();

            cout << "Total Bill: " << finalBill << endl;

           
            if (g.makePayment(finalBill)) {
                admin.bookRoom(hotelRooms[choiceIndex]); 
                cout << "Admin: Room " << roomInput << " is now booked for " << g.name << endl;
            } else {
                cout << "Booking Cancelled due to payment failure." << endl;
            }
        } else {
            cout << "Error: Room is invalid or already occupied!" << endl;
        }
    }

    return 0;
}