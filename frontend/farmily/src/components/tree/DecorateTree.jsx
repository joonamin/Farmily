import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import maintree from '../../assets/images/maintree.png';
import FruitItem from './FruitItem.jsx';
import SmallButton from '../button/SmallButton.jsx';
import axios from '../../api/axios.jsx';
import { useSelector } from 'react-redux';

export default function DecorateTree({ treeFruits, setTreeFruits, handleFruitRightClick }) {
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
    const offsetX = e.currentTarget.offsetLeft;
    const offsetY = e.currentTarget.offsetTop;
    const newX = e.clientX - offsetX - 20;
    const newY = e.clientY - offsetY - 20;
    const isOutsideRelativeDiv =
      e.clientX < offsetX ||
      e.clientX > offsetX + e.currentTarget.offsetWidth ||
      e.clientY < offsetY ||
      e.clientY > offsetY + e.currentTarget.offsetHeight;
    if (!isOutsideRelativeDiv) {
      const updatedFruits = [...treeFruits];
      updatedFruits[draggedFruitIndex] = {
        ...updatedFruits[draggedFruitIndex],
        position: { row: newX, col: newY },
      };
      setTreeFruits(updatedFruits);
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
  const handleTreeFruitRightClick = (e, index) => {
    handleFruitRightClick(e, index);
    setDraggedFruitIndex(null);
  };
  return (
    <div className="relative overflow-hidden" onMouseMove={draggedFruitIndex ? handleMouseMove : null}>
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
          onContextMenu={(e) => handleTreeFruitRightClick(e, index)}
          draggable="true"
          data-fruit-index={index}
        >
          <FruitItem type={fruit.type} title={fruit.recordTitle} id={fruit.recordId} />
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
