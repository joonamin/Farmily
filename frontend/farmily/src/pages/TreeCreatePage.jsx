import LargeButton from '../components/button/LargeButton';
import tree from '../assets/images/treeIcon.png';
import enter from '../assets/images/enterIcon.png';

export default function TreeCreatePage() {
  return (
    <>
      <div className="h-screen text-center align-middle w-full py-16 px-48 z-20">
        <div className="border-8 border-black bg-white h-full rounded-xl p-10">
          <h1 className="text-2xl mb-5">나무 생성하기</h1>
          <div className="flex justify-around">
            <LargeButton url="enter" text="참여하기" image={enter} />
            <LargeButton url="family" text="가족 만들기" image={tree} />
          </div>
        </div>
      </div>
    </>
  );
}
