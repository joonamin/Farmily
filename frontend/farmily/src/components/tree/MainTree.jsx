import { useState, useEffect } from 'react';
import maintree from '../../assets/images/maintree.png';
import FruitItem from './FruitItem.jsx';

export default function MainTree() {
  const [position, setPosition] = useState({ x: 0, y: 0 });
  const [isDragging, setIsDragging] = useState(false);

  const handleMouseDown = (e) => {
    setIsDragging(!isDragging);
  };
  const handleMouseMove = (e) => {
    if (isDragging) {
      const parentOffsetX = e.currentTarget.offsetParent.offsetLeft;
      const parentOffsetY = e.currentTarget.offsetParent.offsetTop;
      const newX = e.clientX - parentOffsetX - 20;
      const newY = e.clientY - parentOffsetY - 20;
      const isOutsideRelativeDiv =
        e.clientX < parentOffsetX ||
        e.clientX >
          parentOffsetX +
            e.currentTarget.offsetParent.offsetParent.offsetWidth ||
        e.clientY < parentOffsetY ||
        e.clientY >
          parentOffsetY +
            e.currentTarget.offsetParent.offsetParent.offsetHeight;
      if (!isOutsideRelativeDiv) {
        setPosition({ x: newX, y: newY });
      }
    }
  };
  return (
    <div className="relative overflow-hidden">
      <img className="mb-24" src={maintree} alt="MainTree" />
      <div
        className="absolute w-10 h-10"
        style={{ top: position.y, left: position.x }}
        onMouseDown={handleMouseDown}
        onMouseMove={handleMouseMove}
      >
        <FruitItem draggable="true" />
      </div>
    </div>
  );
}
