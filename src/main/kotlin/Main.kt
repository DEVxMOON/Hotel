import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.random.Random

fun main() {
    var isExit = false
    val guestList: MutableList<Info> = mutableListOf()
    println("호텔 예약 프로그램입니다.")

    while (!isExit) {
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
                        // 체크인
                        println("체크인 날짜를 입력해주세요 (표기형식: yyyymmdd)")
                        while (true) {
                            checkIn = readln()
                            //체크인 날짜 입력범위 초과 관련 handling
                            try {
                                checkInDate = LocalDate.parse(checkIn, DateTimeFormatter.BASIC_ISO_DATE)
                                if (checkInDate.isBefore(localDate)) {
                                    println("체크인은 지난날은 선택할 수 없습니다.")
                                } else {
                                    break
                                }
                            } catch (e: DateTimeParseException) {
                                println("잘못된 형식입니다. 다시입력해주세요.")
                            } catch (e: Exception) {
                                println("오류 발생: ${e.message}. 다시 입력해주세요.")
                            }
                        }

                        // 체크아웃
                        println("체크아웃 날짜를 입력해주세요 (표기형식: yyyymmdd)")
                        while (true) {
                            checkOut = readln()
                            try {
                                checkOutDate = LocalDate.parse(checkOut, DateTimeFormatter.BASIC_ISO_DATE)
                                if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
                                    println("체크인 날짜보다 이전이거나 같을 수는 없습니다.")
                                } else {
                                    break
                                }
                            } catch (e: DateTimeParseException) {
                                println("잘못된 형식입니다. 다시입력해주세요.")
                            } catch (e: Exception) {
                                println("오류 발생: ${e.message}. 다시 입력해주세요.")
                            }

                        }
                        if (!isRoomAvailable(guestList, room, checkInDate, checkOutDate)) {
                            println("선택한 기간에 해당 방은 이미 예약되어 있습니다. 다른 방을 선택해주세요.")
                            continue
                        } else {
                            break
                        }
                    }
                }


                val guest = Info(name, room, checkIn, checkOut)

                guestList.add(guest)
                println("호텔 예약이 완료되었습니다.")
            }

            2 -> {
                //예약목록 출력
                printGuestList(guestList)
            }

            3 -> {
                //예약목록(정렬) 출력 --> 체크인 날짜 기준 오름차순
                val sortGuestList = guestList.sortBy { LocalDate.parse(it.checkIn, DateTimeFormatter.BASIC_ISO_DATE) }
                println(sortGuestList)
            }

            4 -> {
                //시스템 종료
                println("프로그램을 종료합니다.")
                isExit = true
            }

            5 -> {
                //금액 입금-출금 내열 목록 출력
                println("조회하실 사용자 이름을 입력하세요.")
                val searchName: String = readln()
                val isFound = guestList.find { it.name == searchName }

                if(isFound!= null){
                    guestList.forEach { info ->
                        if(info.name == searchName){
                            println("1. 초기 금액으로 ${info.deposit}원 입금되었습니다.")
                            println("2. 예약금으로 ${info.withdrawal}원 출금되었습니다.")
                        }
                    }
                }else{
                    println("예약된 사용자를 찾을 수 없습니다.")
                }

            }

            6 -> {
                //예약 변경/취소


            }

            else -> {
                println("잘못된 접근입니다.")
            }
        }

    }

}

data class Info(
    var name: String,
    var room: Int,
    var checkIn: String,
    var checkOut: String
){
    var deposit = Random.nextInt(100000,400000)
    var withdrawal = Random.nextInt(30000,50000)
}

// 호텔 예약자 목록 출력 함수
fun printGuestList(list: List<Info>) {
    list.forEachIndexed { id, info ->
        //체크인, 체크아웃 포멧 설정.
        val checkInDate = LocalDate.parse(info.checkIn, DateTimeFormatter.BASIC_ISO_DATE)
        val checkOutDate = LocalDate.parse(info.checkOut, DateTimeFormatter.BASIC_ISO_DATE)
        println("${id + 1}. 사용자: ${info.name}, 방번호: ${info.room}, 체크인: ${checkInDate}, 체크아웃: $checkOutDate")
    }
}

// 해당 기간 내에 방이 예약 되어있는지 확인하는 함수
fun isRoomAvailable(list: List<Info>, room: Int, checkInDate: LocalDate, checkOutDate: LocalDate): Boolean {
    return !list.any {
        it.room == room && LocalDate.parse(it.checkIn, DateTimeFormatter.BASIC_ISO_DATE).let { bookedCheckIn ->
            LocalDate.parse(it.checkOut, DateTimeFormatter.BASIC_ISO_DATE).let { bookedCheckOut ->
                (checkInDate.isBefore(bookedCheckOut) || checkInDate.isEqual(bookedCheckOut)) &&
                        (checkOutDate.isAfter(bookedCheckIn) || checkOutDate.isEqual(bookedCheckIn))
            }
        }
    }
}