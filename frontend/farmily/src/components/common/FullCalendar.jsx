// MyCalendar.js

import React, { Component } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import koLocale from '@fullcalendar/core/locales/ko';

class MyCalendar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isModalOpen: false,
      selectedDate: null,
      newEventTitle: '',
      startDate: null,
      endDate: null,
      allDay: true,
      selectedColor: '#F87171', // 선택된 색상 추가 및 초기화
      events: [
        {
          title: '이벤트 2',
          start: '2024-01-24',
          end: '2024-01-31',
          color: '#60A5FA',
        },
        {
          title: '이벤트 3',
          start: '2024-01-25',
          end: '2024-01-27',
          color: '#60A5FA',
        },
        {
          title: '이벤트 3',
          start: '2024-01-25',
          end: '2024-01-27',
          color: '#60A5FA',
        },
        {
          title: '이벤트 3',
          start: '2024-01-25',
          end: '2024-01-27',
          color: '#60A5FA',
        },
      ],
      isValidationError: false,
      validationErrorMessage: '',
    };
  }

  customDayCellContent = (arg) => {
    return (
      <div onClick={() => this.handleDateClick(arg.date)}>
        {arg.date.getDate()}
      </div>
    );
  };

  handleDateClick = (date) => {
    this.setState({
      isModalOpen: true,
      startDate: date.dateStr,
      endDate: date.dateStr,
      allDay: true,
    });
  };

  handleModalClose = () => {
    this.setState({
      isModalOpen: false,
      selectedDate: null,
      newEventTitle: '',
      isValidationError: false,
      validationErrorMessage: '',
    });
  };

  handleEventTitleChange = (e) => {
    this.setState({
      newEventTitle: e.target.value,
    });
  };

  handleColorChange = (color) => {
    this.setState({
      selectedColor: color,
    });
  };

  handleAddEvent = () => {
    // 시작 날짜가 끝나는 날짜보다 빠른지 검사
    const startDate = new Date(this.state.startDate);
    const endDate = new Date(this.state.endDate + 'T24:00:00');

    if (startDate > endDate) {
      // 날짜가 올바르지 않을 때 경고 상태 업데이트
      this.setState({
        isValidationError: true,
        validationErrorMessage: '종료 날짜가 시작 날짜보다 빠릅니다.',
      });
      return; // 일정 추가 막기
    }

    // 일정 내용이 비어 있는지 확인
    if (this.state.newEventTitle.trim() === '') {
      // 경고 상태 업데이트
      this.setState({
        isValidationError: true,
        validationErrorMessage: '일정 내용을 입력하세요.',
      });
      return; // 일정 추가 막기
    }

    // 경고 상태 초기화
    this.setState({
      isValidationError: false,
      validationErrorMessage: '',
    });

    const newEvent = {
      title: this.state.newEventTitle,
      start: new Date(this.state.startDate),
      end: new Date(this.state.endDate + 'T24:00:00'),
      allDay: this.state.allDay,
      color: this.state.selectedColor, // 선택된 색상 추가
    };

    this.setState((prevState) => ({
      events: [...prevState.events, newEvent],
      selectedColor: '#F87171',
    }));

    this.handleModalClose();

    // 달력을 다시 렌더링하도록 강제 업데이트
    this.forceUpdate();
  };

  render() {
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
          events={this.state.events}
          height="100%"
          locales={[koLocale]}
          selectable="true"
          dayCellContent={this.customDayCellContent}
          dateClick={this.handleDateClick}
          dayMaxEventRows={2}
        />

        {this.state.isModalOpen && (
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
                      {/* 일정 내용이 비어 있을 때 빨간색 글씨로 표시 */}
                      {this.state.isValidationError && (
                        <p className="mt-1 text-sm text-red-500">
                          {this.state.validationErrorMessage}
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
                          onChange={this.handleEventTitleChange}
                          value={this.state.newEventTitle}
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
                          value={this.state.startDate}
                          onChange={(e) =>
                            this.setState({ startDate: e.target.value })
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
                          value={this.state.endDate}
                          onChange={(e) =>
                            this.setState({ endDate: e.target.value })
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
                            onClick={() => this.handleColorChange('#F87171')}
                            className={`w-6 h-6 rounded-full bg-red-400 cursor-pointer mr-2`}
                          ></div>
                          <div
                            onClick={() => this.handleColorChange('#60A5FA')}
                            className={`w-6 h-6 rounded-full bg-blue-400 cursor-pointer mr-2`}
                          ></div>
                          <div
                            onClick={() => this.handleColorChange('#4ADE80')}
                            className={`w-6 h-6 rounded-full bg-green-400 cursor-pointer`}
                          ></div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                  <button
                    onClick={this.handleAddEvent}
                    type="button"
                    className={`w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 text-base font-medium text-white focus:outline-none focus:ring sm:ml-3 sm:w-auto sm:text-sm`}
                    style={{ backgroundColor: this.state.selectedColor }}
                  >
                    추가
                  </button>
                  <button
                    onClick={this.handleModalClose}
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
  }
}

export default MyCalendar;
