import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import Tree from '../components/tree/DecorateTree.jsx';
import Board from '../components/tree/Board.jsx';
import FruitItem from '../components/tree/FruitItem.jsx';
import axios from '../api/axios.jsx';

export default function TreeDecoratePage() {
  const family = useSelector((state) => state.family.value);
  const [treeFruits, setTreeFruits] = useState(family.tree.mainRecordFruitDtoList);
  const [inventoryFruits, setInventoryFruits] = useState([
    {
      id: 0,
      type: '',
      title: '',
    },
  ]);

  useEffect(() => {
    axios
      .get(`family/${family.id}/inventory/${family.mainSprint.sprintId}`)
      .then((response) => {
        setInventoryFruits(response.data.recordFruitList);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const handleInventoryFruitClick = (e, fruitIndex) => {
    const updatedInventoryFruits = inventoryFruits.filter((item, index) => index !== fruitIndex);
    const newTreeFruit = {
      recordId: inventoryFruits[fruitIndex].id,
      recordTitle: inventoryFruits[fruitIndex].title,
      position: {
        row: 0,
        col: 0,
      },
      type: inventoryFruits[fruitIndex].type,
    };
    const updatedTreeFruits = [...treeFruits, newTreeFruit];
    setTreeFruits(updatedTreeFruits);
    setInventoryFruits(updatedInventoryFruits);
  };
  const handleFruitRightClick = (e, fruitIndex) => {
    e.preventDefault();
    const updatedTreeFruits = treeFruits.filter((item, index) => index !== fruitIndex);
    const newInventoryFruit = {
      id: treeFruits[fruitIndex].recordId,
      title: treeFruits[fruitIndex].recordTitle,
      type: treeFruits[fruitIndex].type,
    };
    const updatedInventoryFruits = [...inventoryFruits, newInventoryFruit];
    setTreeFruits(updatedTreeFruits);
    setInventoryFruits(updatedInventoryFruits);
  };

  return (
    <div className="flex items-end">
      <div className="w-28 h-28 ml-5"></div>
      <div className="w-28 h-28 ml-5"></div>
      <div className="w-28 h-28 ml-5"></div>
      <Tree treeFruits={treeFruits} setTreeFruits={setTreeFruits} handleFruitRightClick={handleFruitRightClick} />
      <div>
        <div className="border-4 border-black bg-white w-80 h-80 rounded-md flex flex-col justify-between ml-10">
          <div className="flex flex-wrap">
            {inventoryFruits.map((fruit, index) => (
              <div
                key={index}
                className="w-10 h-10"
                draggable="true"
                data-fruit-index={index}
                onClick={(e) => handleInventoryFruitClick(e, index)}
              >
                <FruitItem type={fruit.type} title={fruit.title} id={fruit.id} />
              </div>
            ))}
          </div>
          <p className="text-center">
            열매 클릭 시 배치가 활성화 되고
            <br /> 우클릭시 배치가 비활성화 됩니다.
          </p>
        </div>
        <Board title="나무 꾸미기" disabled={true} />
      </div>
    </div>
  );
}
