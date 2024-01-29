import tree from '../assets/images/communityTree.png';
import CreateDetail from '../components/common/CreateDetail.jsx';
import SmallButton from '../components/button/SmallButton.jsx';

const TESTITEMS = [
  { date: '2023-07', id: 1, tree: tree },
  { date: '2023-08', id: 2, tree: tree },
  { date: '2023-09', id: 3, tree: tree },
  { date: '2023-10', id: 5, tree: tree },
  { date: '2023-11', id: 7, tree: tree },
  { date: '2023-12', id: 9, tree: tree },
  { date: '2024-01', id: 10, tree: tree },
  { date: '2024-02', id: 12, tree: tree },
];

export default function CommunityWritePage() {
  return (
    <div className="p-5 h-full">
      <div className="w-full h-1/2 pt-10">
        <CreateDetail />
      </div>
      <div className="w-full h-1/3 flex justify-around px-10">
        <div>
          <p className="mb-12">기간 선택하기</p>
          {/* 데이터 받아오고 수정 */}
          <select id="" name="">
            {TESTITEMS.map((item) => {
              return <option key={item.id}>{item.date}</option>;
            })}
          </select>
        </div>
        <div className="h-5/6">
          <p>미리보기</p>
          <img src={tree} alt="" className="h-full" />
        </div>
      </div>
      <div className="flex justify-end pr-4 pt-4">
        {/* 이후 버튼에 create 연결 */}
        <SmallButton text="작성" url="/family/community" />
      </div>
    </div>
  );
}
