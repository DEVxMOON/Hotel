import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main() {
    var isExit = false
    val guestList: MutableList<Info> = mutableListOf()

    while (!isExit) {
        println("호텔 예약 프로그램입니다.")
        println("[메뉴]")
        println("1.방예약 2.예약목록 출력 3.예약목록(정렬) 출력 4.시스템 종료 5.금액 입금-출금내역 목록 출력 6.예약 변경/취소")

        val menuNumber: Int = readln().toInt()

        when (menuNumber) {
            1 -> {
                // booking
                var room: Int
                var checkIn: String
                var checkOut: String
                var checkInDate: LocalDate
                var checkOutDate: LocalDate
                val localDate = LocalDate.now()

                // 이름 입력 받기
                println("예약자분의 성함을 입력해주세요")
                val name = readln()

                // 방 입력
                println("예약할 방번호를 입력해주세요")
                while (true) {
                    room = readln().toInt()
                    if (room < 100 || room > 999) {
                        println("올바르지 않은 방번호입니다. 방번호는 100~999 영역 이내입니다.")
                    } else {
                        break
                    }
                }

                // 체크인
                println("체크인 날짜를 입력해주세요 (표기형식: yyyymmdd)")
                while (true) {
                    checkIn = readln()
                    checkInDate = LocalDate.parse(checkIn, DateTimeFormatter.BASIC_ISO_DATE)
                    if (localDate.isBefore(checkInDate)) {
                        println("체크인은 지난날은 선택할 수 없습니다.")
                    } else {
                        break

                    }
                }

                // 체크아웃
                println("체크아웃 날짜를 입력해주세요 (표기형식: yyyymmdd)")
                while (true) {
                    checkOut = readln()
                    checkOutDate = LocalDate.parse(checkOut, DateTimeFormatter.BASIC_ISO_DATE)

                    if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
                        println("체크인 날짜보다 이전이거나 같을 수는 없습니다.")
                    } else {
                        break
                    }
                }

                val guest = Info(name, room, checkIn, checkOut)

                guestList.add(guest)
                println("호텔 예약이 완료되었습니다.")
            }

            2 -> {
                //예약목록 출력
            }

            3 -> {
                //예약목록(정렬) 출력
            }

            4 -> {
                //시스템 종료
                println("프로그램을 종료합니다.")
                isExit = true
            }

            5 -> {
                //금액 입금-출금 내열 목록 출력
            }

            6 -> {
                //예약 변경/취소
            }
        }

    }

}

data class Info(
    var name: String,
    var room: Int,
    var checkIn: String,
    var checkOut: String
)