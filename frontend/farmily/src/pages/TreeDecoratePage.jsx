import Tree from '../components/tree/DecorateTree.jsx';
import Board from '../components/tree/Board.jsx';

export default function TreeDecoratePage() {
  return (
    <div className="flex items-end">
      <div className="w-28 h-28 ml-5"></div>
      <div className="w-28 h-28 ml-5"></div>
      <div className="w-28 h-28 ml-5"></div>
      <Tree />
      <div>
        <div className="bg-gray-200 w-80 h-80">여기 사용 전 열매 배치</div>
        <Board />
      </div>
    </div>
  );
}
