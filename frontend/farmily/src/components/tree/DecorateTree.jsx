import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import maintree from '../../assets/images/maintree.png';
import FruitItem from './FruitItem.jsx';
import SmallButton from '../button/SmallButton.jsx';
import axios from '../../api/axios.jsx';
import { useSelector } from 'react-redux';

export default function MainTree() {
  const navigate = useNavigate();
  const family = useSelector((state) => state.family.value);
  const [draggedFruitIndex, setDraggedFruitIndex] = useState(null);

  // 임시데이터, 연결 후 열매에 대한 데이터 받아오기
  const [mainRecordFruitDtoList, setMainRecordFruitDtoList] = useState([
    {
      recordId: 1,
      recordTitle: 'string',
      position: { row: 300, col: 100 },
      type: 'DAILY',
    },
    {
      recordId: 2,
      recordTitle: 'string',
      position: { row: 220, col: 150 },
      type: 'DAILY',
    },
    {
      recordId: 3,
      recordTitle: 'string',
      position: { row: 170, col: 200 },
      type: 'DAILY',
    },
  ]);
  const [mainAccessoryFruitDtoList, setMainAccessoryFruitDtoList] = useState([
    {
      id: 7,
      position: {
        row: 220,
        col: 50,
      },
      accessoryType: 'HIDDEN_FRUIT',
    },
    {
      id: 7,
      position: {
        row: 200,
        col: 100,
      },
      accessoryType: 'HIDDEN_FRUIT',
    },
  ]);

  // 과일 클릭 후 배치
  const handleMouseClick = (e) => {
    if (draggedFruitIndex === null) {
      setDraggedFruitIndex(e.currentTarget.dataset.fruitIndex);
    } else {
      setDraggedFruitIndex(null);
    }
  };

  const handleMouseMove = (e, type) => {
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
        if (type === 'record') {
          const updatedFruitList = [...mainRecordFruitDtoList];
          updatedFruitList[draggedFruitIndex] = {
            ...updatedFruitList[draggedFruitIndex],
            position: { row: newX, col: newY },
          };
          setMainRecordFruitDtoList(updatedFruitList);
        } else if (type === 'accessory') {
          const updatedFruitList = [...mainAccessoryFruitDtoList];
          updatedFruitList[draggedFruitIndex] = {
            ...updatedFruitList[draggedFruitIndex],
            position: { row: newX, col: newY },
          };
          setMainAccessoryFruitDtoList(updatedFruitList);
        }
      }
    }
  };

  const handleCancel = () => {
    navigate(`/tree/${family.id}`);
  };
  // 아이템 배치 저장하기
  const handleSave = () => {
    const placementItems = {
      treeId: `${family.id}`,
      placementDtoList: [],
    };
    for (const recordFruit of mainRecordFruitDtoList) {
      placementItems.placementDtoList.push({
        recordId: recordFruit.recordId,
        position: recordFruit.position,
        dtype: 'F',
        type: null,
      });
    }
    for (const accessoryFruit of mainAccessoryFruitDtoList) {
      placementItems.placementDtoList.push({
        recordId: null,
        position: accessoryFruit.position,
        dtype: 'A',
        type: accessoryFruit.accessoryType,
      });
    }
    console.log(placementItems);

    axios
      .post('/family/placement', placementItems)
      .then((response) => {
        console.log(response.data);
        navigate(`/tree/${family.id}`);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="relative overflow-hidden">
      <img className="" src={maintree} alt="MainTree" />
      {mainRecordFruitDtoList.map((fruit, index) => (
        <div
          key={index}
          className="absolute w-10 h-10"
          style={{
            top: fruit.position.col,
            left: fruit.position.row,
          }}
          onClick={handleMouseClick}
          onMouseMove={(e) => handleMouseMove(e, 'record')}
          draggable="true"
          data-fruit-index={index}
        >
          <FruitItem />
        </div>
      ))}
      {mainAccessoryFruitDtoList.map((fruit, index) => (
        <div
          key={index}
          className="absolute w-10 h-10"
          style={{
            top: fruit.position.col,
            left: fruit.position.row,
          }}
          onClick={handleMouseClick}
          onMouseMove={(e) => handleMouseMove(e, 'accessory')}
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
