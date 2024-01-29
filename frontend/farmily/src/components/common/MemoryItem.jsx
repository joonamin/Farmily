import { Link } from 'react-router-dom';
import fruits from '../../assets/images/fruits.png';

export default function MemoryItem(memory) {
  return (
    // 데이터 받아온 후 sprint에 맞는
    <Link to={`/family/record/${memory.id}`}>
      <div className="p-5 snap-center">
        <img src={fruits} alt="" />
        <p>
          2024년 N월 <br />
          수확한 과일바구니
        </p>
      </div>
    </Link>
  );
}
