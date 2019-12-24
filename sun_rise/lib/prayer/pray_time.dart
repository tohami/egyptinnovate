class Salah {
  String name;

  String time;

  int timeHours;

  int timeMinutes;

  Salah(this.name, this.time) {
    List<String> timesSplit = time.split(':');
    timeMinutes = int.parse(timesSplit[1].trim());
    timeHours = int.parse(timesSplit[0].trim());
  }

  Duration get duration => Duration(hours: timeHours, minutes: timeMinutes);
}
