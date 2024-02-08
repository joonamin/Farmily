import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import maintree from '../../assets/images/maintree.png';
import FruitItem from './FruitItem.jsx';
import SmallButton from '../button/SmallButton.jsx';
import axios from '../../api/axios.jsx';
import { useSelector } from 'react-redux';

export default function MainTree({ treeFruits, setTreeFruits }) {
  const navigate = useNavigate();
  const family = useSelector((state) => state.family.value);
  const [draggedFruitIndex, setDraggedFruitIndex] = useState(null);

  // 과일 클릭 후 배치
  const handleMouseClick = (e) => {
    if (draggedFruitIndex === null) {
      setDraggedFruitIndex(e.currentTarget.dataset.fruitIndex);
    } else {
      setDraggedFruitIndex(null);
    }
  };

  // 나무 영역 내에서만 이동 가능하도록 하기
  const handleMouseMove = (e) => {
    if (draggedFruitIndex !== null) {
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
        const updatedFruits = [...treeFruits];
        updatedFruits[draggedFruitIndex] = {
          ...updatedFruits[draggedFruitIndex],
          position: { row: newX, col: newY },
        };
        setTreeFruits(updatedFruits);
      }
    }
  };

  // 아이템 배치 취소
  const handleCancel = () => {
    navigate(`/tree/${family.id}`);
  };

  // 아이템 배치 저장
  const handleSave = () => {
    const placementItems = {
      treeId: `${family.id}`,
      placementDtoList: [],
    };
    for (const treeFruit of treeFruits) {
      placementItems.placementDtoList.push({
        recordId: treeFruit.recordId,
        position: treeFruit.position,
      });
    }

    axios
      .post('/family/placement', placementItems)
      .then((response) => {
        navigate(`/tree/${family.id}`);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="relative overflow-hidden">
      <img className="" src={maintree} alt="MainTree" />
      {treeFruits.map((fruit, index) => (
        <div
          key={index}
          className="absolute w-10 h-10"
          style={{
            top: fruit.position.col,
            left: fruit.position.row,
          }}
          onClick={handleMouseClick}
          onMouseMove={handleMouseMove}
          draggable="true"
          data-fruit-index={index}
        >
          <FruitItem />
        </div>
      ))}

      <div className="flex h-28 items-center justify-center">
        <span onClick={handleSave}>
          <SmallButton text="저장"></SmallButton>
        </span>
        <span onClick={handleCancel}>
          <SmallButton text="취소"></SmallButton>
        </span>
      </div>
    </div>
  );
}
