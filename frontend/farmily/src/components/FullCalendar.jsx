import React, { Component } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import koLocale from '@fullcalendar/core/locales/ko';

class MyCalendar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isModalOpen: false,
      selectedDate: null,
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
      selectedDate: date,
    });
  };

  handleModalClose = () => {
    this.setState({
      isModalOpen: false,
      selectedDate: null,
    });
  };

  render() {
    return (
      <>
        <FullCalendar
          plugins={[dayGridPlugin, interactionPlugin]}
          initialView="dayGridMonth"
          headerToolbar={{
            left: 'prev',
            center: 'title',
            right: 'next',
          }}
          events={[
            { title: '이벤트 1', date: '2024-01-23' },
            { title: '이벤트 2', date: '2024-01-24' },
          ]}
          height="559px"
          locales={[koLocale]}
          dayCellContent={this.customDayCellContent}
        />

        {this.state.isModalOpen && (
          <div style={modalStyle}>
            <h2>일정 추가</h2>
            <p>선택한 날짜: {this.state.selectedDate.toLocaleDateString()}</p>
            {/* TODO: 일정 추가 폼 등을 구현하세요 */}
            <button onClick={this.handleModalClose}>닫기</button>
          </div>
        )}
      </>
    );
  }
}

const modalStyle = {
  position: 'fixed',
  top: '50%',
  left: '57%',
  transform: 'translate(-50%, -50%)',
  backgroundColor: '#87CEEB', // 하늘색으로 수정
  padding: '20px',
  borderRadius: '8px',
  boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
  zIndex: 1000, // 다른 요소보다 위에 위치하도록 zIndex 설정
};

export default MyCalendar;
