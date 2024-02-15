// MyCalendar.js

import React, { useEffect, useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import koLocale from '@fullcalendar/core/locales/ko';
import { useSelector, useDispatch } from 'react-redux';
import axios from '../../api/axios.jsx';

const MyCalendar = () => {
  const dispatch = useDispatch();

  const [events, setEvents] = useState([
    {
      id: 0,
      title: '',
      start: '',
      end: '',
      color: '',
    },
  ]);
  const [isModalOpen, setModalOpen] = useState(false);
  const [selectedDate, setSelectedDate] = useState(null);
  const [newEventTitle, setNewEventTitle] = useState('');
  const [dateRange, setDateRange] = useState({
    startDate: null,
    endDate: null,
  });
  const [allDay, setAllDay] = useState(true);
  const [color, setColor] = useState('#F87171');
  const [isValidationError, setValidationError] = useState(false);
  const [validationErrorMessage, setValidationErrorMessage] = useState('');

  const family = useSelector((state) => state.family.value);

  const customDayCellContent = (arg) => {
    return (
      <div onClick={() => handleDateClick(arg.date)}>{arg.date.getDate()}</div>
    );
  };

  const handleDateClick = (date) => {
    setModalOpen(true);
    setSelectedDate(date.dateStr);
    setDateRange({
      startDate: date.dateStr,
      endDate: date.dateStr,
    });
    setAllDay(true);
  };

  const handleModalClose = () => {
    setModalOpen(false);
    setSelectedDate(null);
    setNewEventTitle('');
    setValidationError(false);
    setValidationErrorMessage('');
  };

  const handleEventTitleChange = (e) => {
    setNewEventTitle(e.target.value);
  };

  const handleColorChange = (selectedColor) => {
    setColor(selectedColor);
  };

  const handleAddEvent = () => {
    const startDate = new Date(dateRange.startDate);
    const endDate = new Date(dateRange.endDate);

    endDate.setDate(endDate.getDate() + 1);

    if (startDate > endDate) {
      setValidationError(true);
      setValidationErrorMessage('종료 날짜가 시작 날짜보다 빠릅니다.');
      return;
    }

    if (newEventTitle.trim() === '') {
      setValidationError(true);
      setValidationErrorMessage('일정 내용을 입력하세요.');
      return;
    }

    setValidationError(false);
    setValidationErrorMessage('');

    const createEvent = {
      dateRange: {
        startDate,
        endDate,
      },
      familyId: family.id,
      content: newEventTitle,
      color: color.slice(1),
    };

    // axios를 이용해서 서버로 데이터 전송
    axios
      .post('/calendar', createEvent)
      .then((res) => {
        // 서버 응답에 따른 추가적인 로직 처리
      })
      .then((res) => {
        axios
          .get(`/calendar/${family.id}`)
          .then((res) => {
            // 서버에서 받아온 데이터를 기존 이벤트 배열에 추가
            const newEvents = res.data.map((d, index) => ({
              id: d.id,
              title: d.content,
              start: d.dateRange.startDate,
              end: d.dateRange.endDate,
              color: '#' + d.color,
            }));

            // 기존 이벤트 배열과 새로 받아온 이벤트를 합쳐서 설정
            setEvents((prevEvents) => [...prevEvents, ...newEvents]);
          })
          .catch((err) => {
            // 에러 처리
            console.error(err);
          });
      })
      .catch((err) => {
        // 에러 처리
        console.error('일정 추가 실패:', err);
      });

    handleModalClose(); // 모달 닫기
  };

  useEffect(() => {
    axios
      .get(`/calendar/${family.id}`)
      .then((res) => {
        // 서버에서 받아온 데이터를 기존 이벤트 배열에 추가
        const newEvents = res.data.map((d, index) => ({
          id: d.id, // 데이터에서 식별 가능한 고유한 값 사용
          title: d.content,
          start: d.dateRange.startDate,
          end: d.dateRange.endDate,
          color: '#' + d.color,
        }));

        // 기존 이벤트 배열과 새로 받아온 이벤트를 합쳐서 설정
        setEvents((prevEvents) => [...prevEvents, ...newEvents]);
      })
      .catch((err) => {
        // 에러 처리
        console.error(err);
      });
  }, [family.id]); // family.id가 변경될 때마다 useEffect 재실행

  return (
    <div className="h-full">
      <FullCalendar
        plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
        initialView="dayGridMonth"
        headerToolbar={{
          left: 'prev',
          center: 'title',
          right: 'next',
        }}
        events={events}
        height="100%"
        locales={[koLocale]}
        selectable="true"
        dayCellContent={customDayCellContent}
        dateClick={handleDateClick}
        dayMaxEventRows={2}
      />

      {isModalOpen && (
        <div className="fixed z-10 inset-0 overflow-y-auto">
          <div className="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div
              className="fixed inset-0 transition-opacity"
              aria-hidden="true"
            >
              <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
            </div>
            <span
              className="hidden sm:inline-block sm:align-middle sm:h-screen"
              aria-hidden="true"
            >
              &#8203;
            </span>
            <div className="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
              <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                <div className="sm:flex sm:items-start">
                  <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                    <h3
                      className="text-lg leading-6 font-medium text-gray-900"
                      id="modal-title"
                    >
                      일정 추가
                    </h3>
                    {isValidationError && (
                      <p className="mt-1 text-sm text-red-500">
                        {validationErrorMessage}
                      </p>
                    )}
                    <div className="mt-2">
                      <label
                        htmlFor="calendar_content"
                        className="block text-sm font-medium text-gray-700"
                      >
                        일정 내용
                      </label>
                      <input
                        type="text"
                        id="calendar_content"
                        name="calendar_content"
                        onChange={handleEventTitleChange}
                        value={newEventTitle}
                        className="mt-1 p-2 w-full border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300"
                      />
                    </div>
                    <div className="mt-2">
                      <label
                        htmlFor="calendar_start_date"
                        className="block text-sm font-medium text-gray-700"
                      >
                        시작 날짜
                      </label>
                      <input
                        type="date"
                        id="calendar_start_date"
                        name="calendar_start_date"
                        value={dateRange.startDate}
                        onChange={(e) =>
                          setDateRange({
                            ...dateRange,
                            startDate: e.target.value,
                          })
                        }
                        className="mt-1 p-2 w-full border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300"
                      />
                    </div>
                    <div className="mt-2">
                      <label
                        htmlFor="calendar_end_date"
                        className="block text-sm font-medium text-gray-700"
                      >
                        종료 날짜
                      </label>
                      <input
                        type="date"
                        id="calendar_end_date"
                        name="calendar_end_date"
                        value={dateRange.endDate}
                        onChange={(e) =>
                          setDateRange({
                            ...dateRange,
                            endDate: e.target.value,
                          })
                        }
                        className="mt-1 p-2 w-full border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300"
                      />
                    </div>
                    <div className="mt-2">
                      <label
                        htmlFor="calendar_color"
                        className="block text-sm font-medium text-gray-700"
                      >
                        일정 색상
                      </label>
                      <div className="flex mt-1">
                        <div
                          onClick={() => handleColorChange('#F87171')}
                          className={`w-6 h-6 rounded-full bg-red-400 cursor-pointer mr-2`}
                        ></div>
                        <div
                          onClick={() => handleColorChange('#60A5FA')}
                          className={`w-6 h-6 rounded-full bg-blue-400 cursor-pointer mr-2`}
                        ></div>
                        <div
                          onClick={() => handleColorChange('#4ADE80')}
                          className={`w-6 h-6 rounded-full bg-green-400 cursor-pointer`}
                        ></div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                <button
                  onClick={handleAddEvent}
                  type="button"
                  className={`w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 text-base font-medium text-white focus:outline-none focus:ring sm:ml-3 sm:w-auto sm:text-sm`}
                  style={{ backgroundColor: color ? color : '#F87171' }}
                >
                  추가
                </button>
                <button
                  onClick={handleModalClose}
                  type="button"
                  className="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring focus:border-blue-300 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm"
                >
                  취소
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default MyCalendar;
