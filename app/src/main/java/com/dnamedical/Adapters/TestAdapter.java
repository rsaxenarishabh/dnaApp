package com.dnamedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.dnamedical.Models.test.AllTest;
import com.dnamedical.Models.test.GrandTest;
import com.dnamedical.Models.test.MiniTest;
import com.dnamedical.Models.test.SubjectTest;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;

import static android.view.View.GONE;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private Context applicationContext;
    private List<GrandTest> grandTests;
    private List<MiniTest> miniTests;
    private List<SubjectTest> subjectTests;
    private List<AllTest> allTests;
    TestAdapter.OnCategoryClick onUserClickCallback;

    public TestAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_test_item, viewGroup, false);
        return new TestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TestAdapter.ViewHolder holder, int i) {

        if (grandTests != null) {
            holder.title.setText(grandTests.get(holder.getAdapterPosition()).getTestName());


            holder.questionTotal.setText((grandTests.get(holder.getAdapterPosition()).getTestQueation()) + "Q's");
            holder.timeTotal.setText(grandTests.get(holder.getAdapterPosition()).getTestDuration());
            holder.textDate.setText(Utils.dateFormat(grandTests.get(holder.getAdapterPosition()).getTime()));

            holder.cardview.setCardBackgroundColor(applicationContext.getResources().getColor(R.color.test_fragment_card_bacckground));

            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(grandTests.get(holder.getAdapterPosition()).getTestDate()).equals(grandTests.get(holder.getAdapterPosition() - 1).getTestDate())) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }
            if (grandTests.get(holder.getAdapterPosition()).getTestPaid().equals("Yes")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
            }


            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(grandTests.get(holder.getAdapterPosition()).getTestId()
                                , grandTests.get(holder.getAdapterPosition()).getTestDuration()
                                , grandTests.get(holder.getAdapterPosition()).getTestName(),
                                grandTests.get(holder.getAdapterPosition()).getTestQueation(),
                                grandTests.get(holder.getAdapterPosition()).getTestPaid(),
                                grandTests.get(holder.getAdapterPosition()).getTestStatus());
                    }
                }
            });

        } else if (miniTests != null) {
            holder.title.setText(miniTests.get(holder.getAdapterPosition()).getTestName());
            holder.questionTotal.setText((miniTests.get(holder.getAdapterPosition()).getTestQueation()) + "Q's");
            holder.timeTotal.setText(miniTests.get(holder.getAdapterPosition()).getTestDuration());
            holder.textDate.setText(Utils.dateFormat(miniTests.get(holder.getAdapterPosition()).getTime()));

            if (miniTests.get(holder.getAdapterPosition()).getTestPaid().equals("Yes")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
            }


            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(miniTests.get(holder.getAdapterPosition())
                        .getTestDate()).equals(miniTests.get(holder.getAdapterPosition() - 1).getTestDate())) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(miniTests.get(holder.getAdapterPosition()).getTestId(),
                                miniTests.get(holder.getAdapterPosition()).getTestDuration(),
                                miniTests.get(holder.getAdapterPosition()).getTestName(),
                                miniTests.get(holder.getAdapterPosition()).getTestQueation(),
                                miniTests.get(holder.getAdapterPosition()).getTestPaid(),
                                miniTests.get(holder.getAdapterPosition()).getTestStatus());
                    }
                }
            });


        } else if (allTests != null) {
            holder.title.setText(allTests.get(holder.getAdapterPosition()).getTestName());
            holder.questionTotal.setText((allTests.get(holder.getAdapterPosition()).getTestQueation()) + "Q's");
            holder.timeTotal.setText(allTests.get(holder.getAdapterPosition()).getTestDuration());
            holder.textDate.setText(Utils.dateFormat(allTests.get(holder.getAdapterPosition()).getTime()));
            Log.d("time", "" + Utils.dateFormat(allTests.get(holder.getAdapterPosition()).getTime()));
            if (allTests.get(holder.getAdapterPosition()).getTestPaid().equals("Yes")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
            }


            if (holder.getAdapterPosition() > 0) {
                if (!allTests.get(holder.getAdapterPosition())
                        .getTestDate().equalsIgnoreCase(allTests.get(holder.getAdapterPosition() - 1)
                                .getTestDate())) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }


           /* if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(allTests.get(holder.getAdapterPosition())
                        .getTestDate()).equals(allTests.get(holder.getAdapterPosition() - 1)
                        .getTestDate())) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }*/
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(allTests.get(holder.getAdapterPosition()).getTestId(),
                                allTests.get(holder.getAdapterPosition()).getTestDuration(),
                                allTests.get(holder.getAdapterPosition()).getTestName(),
                                allTests.get(holder.getAdapterPosition()).getTestQueation(),
                                allTests.get(holder.getAdapterPosition()).getTestPaid(),
                                allTests.get(holder.getAdapterPosition()).getTestStatus());
                    }
                }
            });

        } else if (subjectTests != null) {
            holder.title.setText(subjectTests.get(holder.getAdapterPosition()).getTestName());


            if (subjectTests.get(holder.getAdapterPosition()).getTestQueation() != null) {
                holder.questionTotal.setText((subjectTests.get(holder.getAdapterPosition()).getTestQueation()) + "Q's");
            } else {
                holder.questionTotal.setText("No Q's");

            }
            holder.timeTotal.setText(subjectTests.get(holder.getAdapterPosition()).getTestDuration());
            holder.textDate.setText(Utils.dateFormat(subjectTests.get(holder.getAdapterPosition()).getTime()));
            //  holder.textDate.setText(subjectTests.get(holder.getAdapterPosition()).getTestDate()   );
            if (subjectTests.get(holder.getAdapterPosition()).getTestPaid().equals("Yes")) {
                holder.imageLock.setImageResource(R.drawable.test_lock);
            }


            if (holder.getAdapterPosition() > 0) {
                if (!Objects.requireNonNull(subjectTests.get(holder.getAdapterPosition()).getTestDate())
                        .equals(subjectTests.get(holder.getAdapterPosition() - 1).getTestDate())) {
                    holder.textDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textDate.setVisibility(GONE);
                }
            }
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(subjectTests.get(holder.getAdapterPosition()).getTestId(),
                                subjectTests.get(holder.getAdapterPosition()).getTestDuration(),
                                subjectTests.get(holder.getAdapterPosition()).getTestName(),
                                subjectTests.get(holder.getAdapterPosition()).getTestQueation(),
                                subjectTests.get(holder.getAdapterPosition()).getTestPaid(),
                                subjectTests.get(holder.getAdapterPosition()).getTestStatus());
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (grandTests != null) {
            return grandTests.size();
        } else if (allTests != null) {
            return allTests.size();
        } else if (miniTests != null) {
            return miniTests.size();
        } else if (subjectTests != null) {
            return subjectTests.size();
        } else {
            return 0;
        }
    }


    public void setGrandData(List<GrandTest> testList) {
        // Collections.sort(testList);
        this.grandTests = testList;
    }

    public void setMiniData(List<MiniTest> testList) {
        //Collections.sort(testList);
        this.miniTests = testList;
    }

    public void setAllData(List<AllTest> testList) {
        //Collections.sort(testList);
        this.allTests = testList;
        ;
    }

    public void setSubjectTestsData(List<SubjectTest> testList) {
        // Collections.sort(testList);
        this.subjectTests = testList;
    }


    public void setListener(TestAdapter.OnCategoryClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }

    public interface OnCategoryClick {
        public void onCateClick(String id, String time, String testName, String textQuestion, String testPaid, String TestStatus);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.testTitle)
        TextView title;

        @BindView(R.id.total_question)
        TextView questionTotal;

        @BindView(R.id.total_time)
        TextView timeTotal;

        @BindView(R.id.textView_Date)
        TextView textDate;

        @BindView(R.id.image_lock)
        ImageView imageLock;

        @BindView(R.id.cardView)
        CardView cardview;

        @BindView(R.id.relative_head)
        RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
