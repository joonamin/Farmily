import { useState, useEffect } from 'react';
import MyCalendar from '../components/common/FullCalendar.jsx';

export default function CalendarPage() {
  return (
    <div className="h-full">
      <MyCalendar />
    </div>
  );
}
